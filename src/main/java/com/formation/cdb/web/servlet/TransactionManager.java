package com.formation.cdb.web.servlet;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

@Component
public class TransactionManager implements PlatformTransactionManager {

    @Override
    public void commit(TransactionStatus arg0) throws TransactionException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public TransactionStatus getTransaction(TransactionDefinition arg0) throws TransactionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void rollback(TransactionStatus arg0) throws TransactionException {
        // TODO Auto-generated method stub
        
    }

}
