package spring.app.actuator.endpoints.util;

import org.hibernate.Session;

import java.util.List;

public class EndPointTestFiller {

    /** This is utility meth used in invoke() to get all needed session credentials, form string using them and put on the result list **/
    public static void fillStats(List<String> resultLIst, Session currentSession, String methodName, String ... checkPurposes) {
        if (currentSession.getSessionFactory().getStatistics().getQueries().length != 0) {
            long queries = currentSession.getSessionFactory().getStatistics().getQueries().length;
            long statements = currentSession.getSessionFactory().getStatistics().getPrepareStatementCount();
            long ratio = statements / queries;
            StringBuilder stringBuilder = new StringBuilder (String.format("Method name: %s, query/statement ratio = %s/%s, %s: %s",
                    methodName,
                    queries,
                    statements,
                    checkPurposes[0],
                    ratio > 1 ? "FAILED" : "PASSED"));
            if (checkPurposes.length > 1) {
                long entities = Long.parseLong(checkPurposes[1].split(":")[1]);
                stringBuilder.append(String.format(", %s query/entity ratio = %d/%d: %s",
                        checkPurposes[1].split(":")[0],
                        queries,
                        entities,
                        entities == 0 || entities == 1 ? "PASSED" : statements < entities ? "PASSED" : "FAILED"));
            }
            resultLIst.add(stringBuilder.toString());
        }
    }
}
