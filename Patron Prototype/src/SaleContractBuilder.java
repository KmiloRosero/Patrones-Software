public class SaleContractBuilder implements ContractBuilder {
    @Override
    public Contract build() {
        Contract contract = new Contract();
        contract.setType(ContractType.SALE);
        contract.setPartyARole("Seller");
        contract.setPartyA("Default Seller");
        contract.setPartyBRole("Buyer");
        contract.setPartyB("Default Buyer");
        contract.setAmount(100000.00);
        contract.setDurationMonths(1);
        contract.setCurrency("USD");
        contract.addClause(new Clause("Subject", "Transfer of ownership of the described asset."));
        contract.addClause(new Clause("Price", "The price is paid in a single installment."));
        contract.addClause(new Clause("Delivery", "Delivery takes place upon contract signature."));
        return contract;
    }
}
