package com.food.zotatoFoods.services;

import com.food.zotatoFoods.dto.SmtpEmailDetailsDto;

public interface EmailSenderService {

    public String sendSimpleMail(SmtpEmailDetailsDto SmtpEmailDetails);

    public String sendMailWithAttachment(SmtpEmailDetailsDto SmtpEmailDetails);
}
