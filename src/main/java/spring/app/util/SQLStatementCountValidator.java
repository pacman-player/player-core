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
     *
     * @param expectedSelectCount expected select statement count
     */
    public static void assertSelectCount(long expectedSelectCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long recordedSelectCount = queryCount.getSelect();
        if (expectedSelectCount != recordedSelectCount) {
            throw new SQLSelectCountMismatchException(expectedSelectCount, recordedSelectCount);
        }
    }

    /**
     * Assert select statement count
     *
     * @param message explaining the reason of expectedSelectCount
     * @param expectedSelectCount expected select statement count
     */
    public static void assertSelectCount(String message, long expectedSelectCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long recordedSelectCount = queryCount.getSelect();
        if (expectedSelectCount != recordedSelectCount) {
            throw new SQLSelectCountMismatchException(message, expectedSelectCount, recordedSelectCount);
        }
    }

    /**
     * Assert insert statement count
     *
     * @param expectedInsertCount expected insert statement count
     */
    public static void assertInsertCount(long expectedInsertCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long recordedInsertCount = queryCount.getInsert();
        if (expectedInsertCount != recordedInsertCount) {
            throw new SQLInsertCountMismatchException(expectedInsertCount, recordedInsertCount);
        }
    }

    /**
     * Assert insert statement count
     *
     * @param message explaining the reason of expectedInsertCount
     * @param expectedInsertCount expected insert statement count
     */
    public static void assertInsertCount(String message, long expectedInsertCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long recordedInsertCount = queryCount.getInsert();
        if (expectedInsertCount != recordedInsertCount) {
            throw new SQLInsertCountMismatchException(message, expectedInsertCount, recordedInsertCount);
        }
    }

    /**
     * Assert update statement count
     *
     * @param expectedUpdateCount expected update statement count
     */
    public static void assertUpdateCount(long expectedUpdateCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long recordedUpdateCount = queryCount.getUpdate();
        if (expectedUpdateCount != recordedUpdateCount) {
            throw new SQLUpdateCountMismatchException(expectedUpdateCount, recordedUpdateCount);
        }
    }

    /**
     * Assert update statement count
     *
     * @param message explaining the reason of expectedUpdateCount
     * @param expectedUpdateCount expected update statement count
     */
    public static void assertUpdateCount(String message, long expectedUpdateCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long recordedUpdateCount = queryCount.getUpdate();
        if (expectedUpdateCount != recordedUpdateCount) {
            throw new SQLUpdateCountMismatchException(message, expectedUpdateCount, recordedUpdateCount);
        }
    }

    /**
     * Assert delete statement count
     *
     * @param expectedDeleteCount expected delete statement count
     */
    public static void assertDeleteCount(long expectedDeleteCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long recordedDeleteCount = queryCount.getDelete();
        if (expectedDeleteCount != recordedDeleteCount) {
            throw new SQLDeleteCountMismatchException(expectedDeleteCount, recordedDeleteCount);
        }
    }

    /**
     * Assert delete statement count
     *
     * @param message explaining the reason of expectedDeleteCount
     * @param expectedDeleteCount expected delete statement count
     */
    public static void assertDeleteCount(String message, long expectedDeleteCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long recordedDeleteCount = queryCount.getDelete();
        if (expectedDeleteCount != recordedDeleteCount) {
            throw new SQLDeleteCountMismatchException(message, expectedDeleteCount, recordedDeleteCount);
        }
    }
}
