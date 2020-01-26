package spring.app.util;

import com.vladmihalcea.sql.exception.SQLDeleteCountMismatchException;
import com.vladmihalcea.sql.exception.SQLInsertCountMismatchException;
import com.vladmihalcea.sql.exception.SQLSelectCountMismatchException;
import com.vladmihalcea.sql.exception.SQLUpdateCountMismatchException;
import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;

public class SQLStatementCountValidator {
    private SQLStatementCountValidator() {
    }

    /**
     * Reset the statement recorder
     */
    public static void reset() {
        QueryCountHolder.clear();
    }

    /**
     * Assert select statement count
     * @param expectedSelectCount expected select statement count
     */
    public static void assertSelectCount(int expectedSelectCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        int recordedSelectCount = (int) queryCount.getSelect();
        if(expectedSelectCount != recordedSelectCount) {
            throw new SQLSelectCountMismatchException(expectedSelectCount, recordedSelectCount);
        }
    }

    /**
     * Assert insert statement count
     * @param expectedInsertCount expected insert statement count
     */
    public static void assertInsertCount(int expectedInsertCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        int recordedInsertCount = (int) queryCount.getInsert();
        if(expectedInsertCount != recordedInsertCount) {
            throw new SQLInsertCountMismatchException(expectedInsertCount, recordedInsertCount);
        }
    }

    /**
     * Assert update statement count
     * @param expectedUpdateCount expected update statement count
     */
    public static void assertUpdateCount(int expectedUpdateCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        int recordedUpdateCount = (int) queryCount.getUpdate();
        if(expectedUpdateCount != recordedUpdateCount) {
            throw new SQLUpdateCountMismatchException(expectedUpdateCount, recordedUpdateCount);
        }
    }

    /**
     * Assert delete statement count
     * @param expectedDeleteCount expected delete statement count
     */
    public static void assertDeleteCount(int expectedDeleteCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        int recordedDeleteCount = (int) queryCount.getDelete();
        if(expectedDeleteCount != recordedDeleteCount) {
            throw new SQLDeleteCountMismatchException(expectedDeleteCount, recordedDeleteCount);
        }
    }
}
