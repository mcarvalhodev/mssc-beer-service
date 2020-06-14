package guru.springframework.msscbeerservice.services.order;

import guru.springframework.msscbeerservice.brewery.model.events.ValidateOrderRequest;
import guru.springframework.msscbeerservice.brewery.model.events.ValidateOrderResponse;
import guru.springframework.msscbeerservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {

  private final BeerOrderValidator validator;
  private final JmsTemplate jmsTemplate;

  @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
  public void listen(ValidateOrderRequest request) {
    boolean result = validator.validate(request.getOrder());
    ValidateOrderResponse.ValidateOrderResponseBuilder response =
        ValidateOrderResponse.builder().orderId(request.getOrder().getId()).valid(result);
    jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE, response);
  }
}
