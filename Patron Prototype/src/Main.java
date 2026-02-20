import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContractsFrame().setVisible(true));
    }
}

class ContractsFrame extends JFrame {
    private final TemplateRepository repository = new TemplateRepository();

    private final JComboBox<ContractType> typeCombo = new JComboBox<>(ContractType.values());
    private final JTextField templateCodeField = new JTextField("LEASE_BASE");
    private final JTextField cloneCodeField = new JTextField("LEASE_BASE");
    private final JTextField clientField = new JTextField();
    private final JTextField partyAField = new JTextField();
    private final JTextField partyBField = new JTextField();
    private final JTextField amountField = new JTextField();
    private final JTextField durationField = new JTextField();
    private final JTextField currencyField = new JTextField("USD");
    private final JTextField clauseTitleField = new JTextField();
    private final JTextField clauseContentField = new JTextField();
    private final JTextArea outputArea = new JTextArea();

    private final JLabel partyARoleLabel = new JLabel("Party A");
    private final JLabel partyBRoleLabel = new JLabel("Party B");

    ContractsFrame() {
        setTitle("Legal Contract System");
        setSize(920, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(createTemplatePanel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.CENTER);
        add(createOutputPanel(), BorderLayout.SOUTH);

        updateRolesByType();
    }

    private JPanel createTemplatePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Builder: create base template"));

        JButton createTemplateButton = new JButton("Create base template");
        createTemplateButton.addActionListener(e -> createBaseTemplate());
        typeCombo.addActionListener(e -> updateRolesByType());

        panel.add(new JLabel("Contract type"));
        panel.add(typeCombo);
        panel.add(new JLabel("Template code"));
        panel.add(templateCodeField);
        panel.add(new JLabel("Code to clone"));
        panel.add(cloneCodeField);
        panel.add(new JLabel());
        panel.add(createTemplateButton);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 4, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Prototype: clone and customize"));

        JButton cloneButton = new JButton("Clone and customize");
        cloneButton.addActionListener(e -> cloneAndCustomize());

        panel.add(new JLabel("Client"));
        panel.add(clientField);
        panel.add(partyARoleLabel);
        panel.add(partyAField);

        panel.add(partyBRoleLabel);
        panel.add(partyBField);
        panel.add(new JLabel("Amount"));
        panel.add(amountField);

        panel.add(new JLabel("Duration (months)"));
        panel.add(durationField);
        panel.add(new JLabel("Currency"));
        panel.add(currencyField);

        panel.add(new JLabel("New clause title"));
        panel.add(clauseTitleField);
        panel.add(new JLabel("New clause content"));
        panel.add(clauseContentField);

        panel.add(new JLabel());
        panel.add(cloneButton);
        panel.add(new JLabel());
        panel.add(new JLabel());

        return panel;
    }

    private JScrollPane createOutputPanel() {
        outputArea.setEditable(false);
        outputArea.setRows(12);
        return new JScrollPane(outputArea);
    }

    private void createBaseTemplate() {
        String code = templateCodeField.getText().trim();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a template code.");
            return;
        }

        ContractBuilder builder = getBuilderByType((ContractType) typeCombo.getSelectedItem());
        Contract template = builder.build();
        repository.registerTemplate(code, template);
        cloneCodeField.setText(code);
        loadContractIntoForm(template);
        outputArea.setText("Template created: " + code + "\n\n" + template);
    }

    private void cloneAndCustomize() {
        String code = cloneCodeField.getText().trim();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a template code to clone.");
            return;
        }

        try {
            Contract contract = repository.cloneTemplate(code);
            contract.setClient(clientField.getText().trim());
            contract.setPartyA(partyAField.getText().trim());
            contract.setPartyB(partyBField.getText().trim());
            contract.setAmount(parseDouble(amountField.getText().trim(), "Amount"));
            contract.setDurationMonths(parseInt(durationField.getText().trim(), "Duration (months)"));
            contract.setCurrency(currencyField.getText().trim());

            String title = clauseTitleField.getText().trim();
            String content = clauseContentField.getText().trim();
            if (!title.isEmpty() && !content.isEmpty()) {
                contract.addClause(new Clause(title, content));
            }

            outputArea.setText(
                    "Available templates: " + repository.availableCodes() +
                            "\n\nCloned and customized contract:\n" + contract
            );
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private ContractBuilder getBuilderByType(ContractType type) {
        if (type == ContractType.LEASE) {
            return new LeaseContractBuilder();
        }
        if (type == ContractType.SALE) {
            return new SaleContractBuilder();
        }
        return new EmploymentContractBuilder();
    }

    private void loadContractIntoForm(Contract contract) {
        partyARoleLabel.setText(contract.getPartyARole());
        partyBRoleLabel.setText(contract.getPartyBRole());
        partyAField.setText(contract.getPartyA());
        partyBField.setText(contract.getPartyB());
        amountField.setText(String.valueOf(contract.getAmount()));
        durationField.setText(String.valueOf(contract.getDurationMonths()));
        currencyField.setText(contract.getCurrency());
    }

    private void updateRolesByType() {
        ContractType type = (ContractType) typeCombo.getSelectedItem();
        if (type == ContractType.LEASE) {
            partyARoleLabel.setText("Landlord");
            partyBRoleLabel.setText("Tenant");
            templateCodeField.setText("LEASE_BASE");
            return;
        }
        if (type == ContractType.SALE) {
            partyARoleLabel.setText("Seller");
            partyBRoleLabel.setText("Buyer");
            templateCodeField.setText("SALE_BASE");
            return;
        }
        partyARoleLabel.setText("Employer");
        partyBRoleLabel.setText("Employee");
        templateCodeField.setText("EMP_BASE");
    }

    private double parseDouble(String value, String fieldName) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(fieldName + " is invalid.");
        }
    }

    private int parseInt(String value, String fieldName) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(fieldName + " is invalid.");
        }
    }
}
