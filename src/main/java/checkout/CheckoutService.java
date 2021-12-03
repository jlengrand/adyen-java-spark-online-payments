package checkout;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.model.Amount;
import com.adyen.model.checkout.CreateCheckoutSessionRequest;
import com.adyen.model.checkout.CreateCheckoutSessionResponse;
import com.adyen.service.Checkout;
import com.adyen.service.exception.ApiException;

public class CheckoutService {

    private final Checkout checkout;
    private final String merchantAccount;

    public CheckoutService(final Properties prop) {
        merchantAccount = prop.getProperty("merchantAccount");
        checkout = new Checkout(new Client(prop.getProperty("apiKey"), Environment.TEST));
    }

    public CreateCheckoutSessionResponse checkoutsessions() throws IOException, ApiException {
        String orderRef = UUID.randomUUID().toString();
        Amount amount = new Amount()
                .currency("EUR")
                .value(1000L);
        CreateCheckoutSessionRequest checkoutSession = new CreateCheckoutSessionRequest();
        checkoutSession.merchantAccount(merchantAccount);
        checkoutSession.setChannel(CreateCheckoutSessionRequest.ChannelEnum.WEB);
        checkoutSession.setReference(orderRef); // required
        checkoutSession.setReturnUrl("http://localhost:8080/redirect?orderRef=" + orderRef);
        checkoutSession.setAmount(amount);
        return checkout.sessions(checkoutSession);
    }
}
