import java.util.ArrayList;
import java.util.List;

public class Contract {
    private ContractType type;
    private String client;
    private String partyARole;
    private String partyA;
    private String partyBRole;
    private String partyB;
    private double amount;
    private int durationMonths;
    private String currency;
    private List<Clause> clauses;

    public Contract() {
        this.currency = "USD";
        this.clauses = new ArrayList<>();
    }

    public Contract(Contract other) {
        this.type = other.type;
        this.client = other.client;
        this.partyARole = other.partyARole;
        this.partyA = other.partyA;
        this.partyBRole = other.partyBRole;
        this.partyB = other.partyB;
        this.amount = other.amount;
        this.durationMonths = other.durationMonths;
        this.currency = other.currency;
        this.clauses = new ArrayList<>();
        for (Clause clause : other.clauses) {
            this.clauses.add(new Clause(clause));
        }
    }

    public Contract cloneContract() {
        return new Contract(this);
    }

    public void addClause(Clause clause) {
        this.clauses.add(clause);
    }

    public ContractType getType() {
        return type;
    }

    public void setType(ContractType type) {
        this.type = type;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPartyARole() {
        return partyARole;
    }

    public void setPartyARole(String partyARole) {
        this.partyARole = partyARole;
    }

    public String getPartyA() {
        return partyA;
    }

    public void setPartyA(String partyA) {
        this.partyA = partyA;
    }

    public String getPartyBRole() {
        return partyBRole;
    }

    public void setPartyBRole(String partyBRole) {
        this.partyBRole = partyBRole;
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Clause> getClauses() {
        return clauses;
    }

    public void setClauses(List<Clause> clauses) {
        this.clauses = clauses;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "type=" + type +
                ", client='" + client + '\'' +
                ", " + partyARole + "='" + partyA + '\'' +
                ", " + partyBRole + "='" + partyB + '\'' +
                ", amount=" + amount +
                ", durationMonths=" + durationMonths +
                ", currency='" + currency + '\'' +
                ", clauses=" + clauses +
                '}';
    }
}
