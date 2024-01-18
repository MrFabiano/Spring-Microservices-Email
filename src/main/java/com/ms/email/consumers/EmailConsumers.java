package com.ms.email.consumers;

import com.ms.email.dtos.EmailRecordDTO;
import com.ms.email.model.EmailModel;
import com.ms.email.repository.EmailRepository;
import com.ms.email.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumers {

    final EmailRepository emailRepository;
    final EmailService emailService;

    public EmailConsumers(EmailRepository emailRepository, EmailService emailService) {
        this.emailRepository = emailRepository;
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDTO emailRecordDTO){
        //System.out.println(emailRecordDTO.emailTo());
        var emailModel = new EmailModel();
        BeanUtils.copyProperties(emailRecordDTO, emailModel);
        emailService.sendEmail(emailModel);

    }
}
