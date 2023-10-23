import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Gym {
    public Gym() {
        ArrayList<Customer> customers = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String userInput = JOptionPane.showInputDialog("Skriv in ditt personnummer eller namn");

        if (userInput == null) {
            JOptionPane.showMessageDialog(null, "Programmet avslutades");
            return;
        }

        try (BufferedReader customerData = new BufferedReader(new FileReader("src/customers.txt"));
             BufferedWriter ptPaymentFile = new BufferedWriter(new FileWriter("ptFile.txt", true))) {

            String tempLine;
            boolean personFound = false;

            while ((tempLine = customerData.readLine()) != null) {
                String[] nameAndId = tempLine.split(", ");
                String idNumber = nameAndId[0];
                String name = nameAndId[1];

                String paymentDateString = customerData.readLine();
                LocalDate paymentDate = LocalDate.parse(paymentDateString, dtf);
                customers.add(new Customer(name, idNumber, paymentDate));

                for (Customer thisCustomer : customers) {
                    if (thisCustomer.getIdNumber().equals(userInput) || thisCustomer.getName().equalsIgnoreCase(userInput)) {
                        personFound = true;
                        LocalDate currentDate = LocalDate.now();
                        LocalDate oneYearAgo = currentDate.minusYears(1);

                        if (thisCustomer.getPaymentDate().isAfter(oneYearAgo)) {
                            JOptionPane.showMessageDialog(null, "Kunden är en nuvarande medlem");
                            ptPaymentFile.write(thisCustomer.getName() + ", " + thisCustomer.getIdNumber() + "\n" + currentDate.format(dtf) + "\n");

                        } else {
                            JOptionPane.showMessageDialog(null, "Kunden är en f.d. kund");
                        }
                    }
                }

                if (personFound) {
                    break;
                }
            }

            if (!personFound) {
                JOptionPane.showMessageDialog(null, "Personen existerar inte eller är obehörig");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Det uppstod ett fel i filhanteringen");
        } catch (Exception e) {
            System.out.println("Ett okänt fel uppstod");
        }
    }

    public static void main(String[] args) {
        Gym gym = new Gym();
    }
}
