package com.kacielriff.authentication_project.service;

import com.kacielriff.authentication_project.enums.EmailType;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final Configuration freemarkerConfiguration;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${email.hostname.url}")
    private String hostname;

    private final JavaMailSender mailSender;

    public String generateEmailContent(String userEmail, EmailType emailType, String token)
            throws IOException, TemplateException, MessagingException {

        String subject = getSubjectByType(emailType);
        String url = getUrlByType(emailType);

        if (emailType.equals(EmailType.ACCOUNT_CONFIRMATION)
                || emailType.equals(EmailType.PASSWORD_RECOVERY)) {
            url = url + "?" + token;
        }

        Map<String, Object> model = new HashMap<>();

        model.put("subject", subject);
        model.put("content", getContentByType(emailType));
        model.put("url", url);
        model.put("buttonLabel", getButtonLabelByType(emailType));
        model.put("expirationTime", getExpirationTimeByType(emailType));

        Template template = freemarkerConfiguration.getTemplate("email-template-universal.ftl");

        String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        sendEmail(userEmail, "Auth Project - " + subject, htmlContent);

        return htmlContent;
    }

    private void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    private String getSubjectByType(EmailType emailType) {
        return switch (emailType) {
            case REGISTRATION_SUCCESS -> "Cadastro Realizado com Sucesso";
            case ACCOUNT_CONFIRMATION -> "Confirmar de Conta";
            case PASSWORD_RECOVERY -> "Recuperar de Senha";
        };
    }

    private String getContentByType(EmailType emailType) {
        return switch (emailType) {
            case REGISTRATION_SUCCESS ->
                    "Seu cadastro foi realizado com sucesso no <strong>Authorization Project</strong>.<p>Agora você pode acessar a plataforma e começar a utilizar todos os recursos disponíveis.</p>";
            case ACCOUNT_CONFIRMATION ->
                    "Obrigado por escolher a <strong>Authorization Project</strong>.<p>Para concluir seu cadastro será necessário confirmar sua conta clicando no botão abaixo.</p>";
            case PASSWORD_RECOVERY ->
                    "Recebemos uma solicitação para redefinir sua senha.<p>Para redefinir sua senha, clique no botão abaixo:</p>";
        };
    }

    private String getUrlByType(EmailType emailType) {
        return switch (emailType) {
            case REGISTRATION_SUCCESS -> hostname;
            case ACCOUNT_CONFIRMATION -> hostname + "/account-confirmation";
            case PASSWORD_RECOVERY -> hostname + "/password-recovery";
        };
    }

    private String getButtonLabelByType(EmailType emailType) {
        return switch (emailType) {
            case REGISTRATION_SUCCESS -> "Entrar";
            case ACCOUNT_CONFIRMATION -> "Confirmar Conta";
            case PASSWORD_RECOVERY -> "Recuperar Senha";
        };
    }

    private String getExpirationTimeByType(EmailType emailType) {
        return switch (emailType) {
            case REGISTRATION_SUCCESS -> "";
            case ACCOUNT_CONFIRMATION -> "10 dias";
            case PASSWORD_RECOVERY -> "4 horas";
        };
    }
}