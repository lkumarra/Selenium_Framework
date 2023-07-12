package com.org.bank.listeners;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.monitor.query.LogsQueryClient;
import com.azure.monitor.query.LogsQueryClientBuilder;
import com.azure.monitor.query.models.LogsQueryResult;
import com.azure.monitor.query.models.LogsTableRow;
import com.azure.monitor.query.models.QueryTimeInterval;

import java.time.Duration;

public class TestAzureMain {

    public static void main(String[] args) {
        System.setProperty("AZURE_CLIENT_ID", "2b937d62-df99-4cbc-96c9-0ed07adde40a");
        System.setProperty("AZURE_TENANT_ID", "9bbbcb03-4dc3-44a3-a63e-0b490987f4cc");
        System.setProperty("AZURE_CLIENT_SECRET", "datalakekeysqa");
        LogsQueryClient logsQueryClient = new LogsQueryClientBuilder()
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
        String resourceId = "/subscriptions/f45c7541-6906-461d-9a4c-8046388d2a2d/resourceGroups/rgEUS_RyderConnect_QA/providers/microsoft.insights/components/RyderConnect-QA";
        String query = "customEvents\r\n"
                + "	| where name contains \"RConn.SnapshotProcessor.SqlHandler\"";
        LogsQueryResult queryResults = logsQueryClient.queryResource(resourceId, query,
                new QueryTimeInterval(Duration.ofDays(2)));

        for (LogsTableRow row : queryResults.getTable().getRows()) {
            System.out.println(row.getColumnValue("OperationName") + " " + row.getColumnValue("ResourceGroup"));
        }
        //AzureKeyVault_TenantId Value="9bbbcb03-4dc3-44a3-a63e-0b490987f4cc"
        //AzureKeyVault_VaultName Value="datalakekeysqa"
        //AzureKeyVault_ClientId Value="2b937d62-df99-4cbc-96c9-0ed07adde40a"
        //
        //
        //
        //
        //AZURE_CLIENT_ID, AZURE_TENANT_ID, AZURE_CLIENT_SECRET.
    }
}
