//package spring.app.util;
//
//import org.hibernate.EmptyInterceptor;
//import org.hibernate.type.Type;
//import org.springframework.data.domain.Auditable;
//
//import java.io.Serializable;
//import java.util.Date;
//
//public class AuditInterceptor extends EmptyInterceptor {
//
//    private static int updates;
//    private static int creates;
//    private static int loads;
//
//    public void resetUpdatesCreatesLoads() {
//        this.updates = 0;
//        this.creates = 0;
//        this.loads = 0;
//    }
//
//    public int getUpdates() {
//        return updates;
//    }
//
//    public int getCreates() {
//        return creates;
//    }
//
//    public int getLoads() {
//        return loads;
//    }
//
//    public void onDelete(Object entity,
//                         Serializable id,
//                         Object[] state,
//                         String[] propertyNames,
//                         Type[] types) {
//        // do nothing
//    }
//
//    public boolean onFlushDirty(Object entity,
//                                Serializable id,
//                                Object[] currentState,
//                                Object[] previousState,
//                                String[] propertyNames,
//                                Type[] types) {
//
//        if ( entity instanceof Auditable) {
//            updates++;
//            for ( int i=0; i < propertyNames.length; i++ ) {
//                if ( "lastUpdateTimestamp".equals( propertyNames[i] ) ) {
//                    currentState[i] = new Date();
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public boolean onLoad(Object entity,
//                          Serializable id,
//                          Object[] state,
//                          String[] propertyNames,
//                          Type[] types) {
//        if ( entity instanceof Auditable ) {
//            loads++;
//        }
//        return false;
//    }
//
//    public boolean onSave(Object entity,
//                          Serializable id,
//                          Object[] state,
//                          String[] propertyNames,
//                          Type[] types) {
//
//        if ( entity instanceof Auditable ) {
//            creates++;
//            for ( int i=0; i<propertyNames.length; i++ ) {
//                if ( "createTimestamp".equals( propertyNames[i] ) ) {
//                    state[i] = new Date();
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
////    public void afterTransactionCompletion(Transaction tx) {
////        if ( tx. ) {
////            System.out.println("Creations: " + creates + ", Updates: " + updates + "Loads: " + loads);
////        }
////        updates=0;
////        creates=0;
////        loads=0;
////    }
//
//}