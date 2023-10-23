import java.time.LocalDate;

public class Customer {
    private final String name;
    private final String idNumber;
    private final LocalDate paymentDate;

    public Customer(String name, String idNumber, LocalDate paymentDate) {
        this.name = name;
        this.idNumber = idNumber;
        this.paymentDate = paymentDate;
    }

    public String getName() {
        return name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }
}


