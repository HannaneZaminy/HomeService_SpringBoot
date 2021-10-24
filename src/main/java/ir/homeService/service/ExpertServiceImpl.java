package ir.homeService.service;

import ir.homeService.data.domain.Expert;
import ir.homeService.data.domain.SubService;
import ir.homeService.data.domain.Users;
import ir.homeService.data.enums.UserSituation;
import ir.homeService.data.repository.ExpertRepository;
import ir.homeService.data.repository.SubServiceRepository;
import ir.homeService.data.repository.UserRepository;
import ir.homeService.dto.AddSubServiceToExpertDto;
import ir.homeService.dto.ExpertDto;
import ir.homeService.dto.SubServiceDto;
import ir.homeService.service.exception.DuplicatedEmailAddressException;
import ir.homeService.service.exception.DuplicatedSubServiceException;
import ir.homeService.service.exception.NotFoundExpertException;
import ir.homeService.service.exception.NotFoundSubServiceException;
import ir.maktab.service.exception.*;
import ir.homeService.service.mapper.ExpertMapper;
import net.bytebuddy.utility.RandomString;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ExpertServiceImpl implements ExpertService {
    private final ExpertRepository expertRepository;
    private final ExpertMapper expertMapper;
    private final SubServiceRepository subServiceRepository;
    private final MessageSource messageSource;
    private  final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    public ExpertServiceImpl(ExpertRepository expertRepository, ExpertMapper expertMapper, SubServiceRepository subServiceRepository,
                             MessageSource messageSource, PasswordEncoder passwordEncoder, JavaMailSender mailSender, UserRepository userRepository) {
        this.expertRepository = expertRepository;
        this.expertMapper = expertMapper;
        this.subServiceRepository = subServiceRepository;
        this.messageSource = messageSource;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    @Override
    public ExpertDto registerExpert(ExpertDto dto, String siteURL) throws DuplicatedEmailAddressException,
            UnsupportedEncodingException, MessagingException {
        Optional<Expert> optionalExpert = expertRepository.findByEmail(dto.getEmail());
        if (optionalExpert.isPresent()) {
            throw new DuplicatedEmailAddressException(
                    messageSource.getMessage("duplicated.email.address",null,new Locale("en_us")));
        }
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        dto.setVerificationCode(randomCode);
        dto.setEnabled(false);
        dto.setUserSituation(UserSituation.Pending_approval);

        expertRepository.save(expertMapper.toExpert(dto));
        sendVerificationEmail(dto, siteURL);
        return dto;
    }

    @Override
    public void sendVerificationEmail(ExpertDto dto, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String toAddress = dto.getEmail();
        String fromAddress = "zahra.asgr1996@gmail.com";
        String senderName = "zahra asgari";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "zahra asgari...";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", dto.getName());
        String verifyURL = siteURL + "/expert/verify?code=" + dto.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    @Override
    public boolean verify(String verificationCode) {
        Users user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            user.setUserSituation(UserSituation.Pending_approval);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public void deleteExpert(ExpertDto expert) {
        expertRepository.delete(expertMapper.toExpert(expert));
    }

    @Override
    public void updateExpert(ExpertDto expert) {
        expertRepository.save(expertMapper.toExpert(expert));
    }

    @Override
    public List<ExpertDto> fetchAllExperts() {
        return expertRepository.findAll()
                .stream()
                .map(expertMapper::toExpertDto)
                .collect(Collectors.toList());
    }

    @Override
    public ExpertDto findByEmail(String email) throws NotFoundExpertException {
        Optional<Expert> expert = expertRepository.findByEmail(email);
        if (expert.isPresent()) {
            return expertMapper.toExpertDto(expert.get());
        }
        throw new NotFoundExpertException(messageSource.getMessage("not.found.expert",null,new Locale("en_us")));

    }

    @Override
    public void addExpertToSubService(SubServiceDto service, ExpertDto expert) throws NotFoundSubServiceException, NotFoundExpertException, DuplicatedSubServiceException {
        Optional<SubService> subService = subServiceRepository.findById(service.getId());
        Optional<Expert> expertOptional = expertRepository.findByEmail(expert.getEmail());
        if (!subService.isPresent()){
            throw new NotFoundSubServiceException(messageSource.getMessage("not.found.sub.service",null,new Locale("en_us")));
        }if (!expertOptional.isPresent()){
            throw  new NotFoundExpertException(messageSource.getMessage("not.found.expert",null,new Locale("en_us")));
        }
        if(expertOptional.get().getServices().contains(subService.get())){
            throw new DuplicatedSubServiceException(messageSource.getMessage("sub.service.exist.in.expert.list",null,new Locale("en_us")));
        }
        expertOptional.get().getServices().add(subService.get());
        expertRepository.save(expertOptional.get());

    }


    @Override
    public void changePassword(ExpertDto dto) {
        Optional<Expert> expert = expertRepository.findByEmail(dto.getEmail());
        Expert expert1 = expert.get();
        expert1.setPassword(dto.getPassword());
        expertRepository.save(expert1);
    }

    @Override
    public Double showAvgRate(ExpertDto dto) throws NotFoundExpertException {
        Optional<Expert> byEmail = expertRepository.findByEmail(dto.getEmail());
        if (!byEmail.isPresent()){
            throw new NotFoundExpertException(messageSource.getMessage("not.found.expert",null,new Locale("en_us")));
        }
        return byEmail.get().getRate();
    }

    @Override
    public Double getBalance(ExpertDto user) {
        Optional<Expert> byEmail = expertRepository.findByEmail(user.getEmail());
        return byEmail.get().getCredit();
    }

    @Override
    public void addSubServiceToExpertList(AddSubServiceToExpertDto dto) throws DuplicatedSubServiceException {
        Optional<Expert> expert = expertRepository.findById(dto.getExpertId());
        Optional<SubService> subService = subServiceRepository.findById(dto.getSubServiceId());
        if (expert.get().getServices().contains(subService.get())){
            throw new DuplicatedSubServiceException(messageSource.getMessage("sub.service.exist.in.expert.list",null,new Locale("en_us")));
        }
        expert.get().getServices().add(subService.get());
        expertRepository.save(expert.get());
    }
}
