public class StatementFormatter {

    public static String buildStatement(Account account) {
        StringBuilder sb = new StringBuilder();
        sb.append("==============================\n");
        sb.append("      MINIBANK STATEMENT\n");
        sb.append("==============================\n");
        sb.append("Account No : ").append(account.getAccountNumber()).append('\n');
        sb.append("Owner      : ").append(account.getOwnerName()).append('\n');
        sb.append("Balance    : Rs. ").append(account.getBalance()).append('\n');
        sb.append("Status     : ").append(account.isActive() ? "ACTIVE" : "CLOSED").append('\n');
        sb.append("==============================");
        return sb.toString();
    }
}
