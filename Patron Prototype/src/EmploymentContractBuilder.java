public class EmploymentContractBuilder implements ContractBuilder {
    @Override
    public Contract build() {
        Contract contract = new Contract();
        contract.setType(ContractType.EMPLOYMENT);
        contract.setPartyARole("Employer");
        contract.setPartyA("Default Employer");
        contract.setPartyBRole("Employee");
        contract.setPartyB("Default Employee");
        contract.setAmount(3000.00);
        contract.setDurationMonths(24);
        contract.setCurrency("USD");
        contract.addClause(new Clause("Position", "The employee will perform legal analyst duties."));
        contract.addClause(new Clause("Schedule", "Working hours are Monday through Friday."));
        contract.addClause(new Clause("Confidentiality", "Client information is strictly confidential."));
        return contract;
    }
}
