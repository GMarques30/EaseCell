package com.easecell.ease_cell.account.infra.gateway;

import com.easecell.ease_cell.account.application.dto.SendEmailInput;
import com.easecell.ease_cell.account.application.gateway.EmailGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Component
public class SESGateway implements EmailGateway {
  private final SesClient client;

  public SESGateway(@Value("${aws.access-key}") String accessKey,
                    @Value("${aws.secret-key}") String secretKey,
                    @Value("${aws.region}") String region) {
    var credentials = AwsBasicCredentials.create(accessKey, secretKey);
    this.client = SesClient.builder()
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .region(Region.of(region))
            .build();
  }

  @Override
  public void sendEmail(SendEmailInput input) {
    try {
      var subject = "[EaseCell] Recuperação de senha";
      var htmlMessage = """
                    <!DOCTYPE html>
                                <html>
                                <head>
                                    <style>
                                        body { font-family: Arial, sans-serif; color: #333; }
                                        .container { width: 100%; max-width: 600px; margin: 0 auto; }
                                        .header { background-color: #4CAF50; color: white; padding: 10px; text-align: center; }
                                        .content { padding: 20px; }
                    		    .footer { background-color: #f1f1f1; color: #333; text-align: center; padding: 10px; font-size: 12px; }
                                    </style>
                                </head>
                                <body>
                                    <div class="container">
                                        <div class="header">
                                            <h1>Ease Cell</h1>
                                        </div>
                                        <div class="content">
                                            <p>Olá {name},</p>
                                            <p>Parece que uma troca de senha para sua conta foi solicitada.</p>
                    			<p>Se foi você, então clique no link abaixo para digitar uma nova senha:</p>
                                            <p>
                        				<a href="{link}">Resetar minha senha</a>
                      			</p>
                      			<p>Se não foi você, então descarte esse email!</p>
                      			<p>
                        				Obrigado! <br />
                        				<strong>Equipe Ease Cell</strong>
                      			</p>
                                        </div>
                    		    <div class="footer">
                                            <p>&copy Ease Cell</p>
                                        </div>
                                    </div>
                                </body>
                                </html>
                    """;

      htmlMessage = htmlMessage.replace("{name}", input.name())
              .replace("{link}", input.link());

      SendEmailRequest request = SendEmailRequest.builder()
              .destination(Destination.builder()
                      .toAddresses(input.to())
                      .build())
              .message(Message.builder()
                      .subject(Content.builder()
                              .data(subject)
                              .charset("UTF-8")
                              .build())
                      .body(Body.builder()
                              .html(Content.builder()
                                      .data(htmlMessage)
                                      .charset("UTF-8")
                                      .build())
                              .build())
                      .build())
              .source("gonot82746@rabitex.com")
              .build();
      this.client.sendEmail(request);
    } catch(SesException e) {
      throw new RuntimeException("The email was not sent. Error message: " + e.awsErrorDetails().errorMessage(), e);
    }
  }
}
