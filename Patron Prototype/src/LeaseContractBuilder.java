public class LeaseContractBuilder implements ContractBuilder {
    @Override
    public Contract build() {
        Contract contract = new Contract();
        contract.setType(ContractType.LEASE);
        contract.setPartyARole("Landlord");
        contract.setPartyA("Default Landlord");
        contract.setPartyBRole("Tenant");
        contract.setPartyB("Default Tenant");
        contract.setAmount(2000.00);
        contract.setDurationMonths(12);
        contract.setCurrency("USD");
        contract.addClause(new Clause("Subject", "The property is leased for commercial use."));
        contract.addClause(new Clause("Term", "The initial term is 12 months."));
        contract.addClause(new Clause("Payment", "Monthly payment must be made within the first 5 days."));
        return contract;
    }
}
