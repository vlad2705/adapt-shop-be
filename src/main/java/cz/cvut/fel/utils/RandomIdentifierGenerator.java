package cz.cvut.fel.utils;

import org.hibernate.id.IdentifierGenerator;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.SecureRandom;

@Service
public class RandomIdentifierGenerator implements IdentifierGenerator {
    
    private final static SecureRandom sr = new SecureRandom();
    
    public Serializable generate(SessionImplementor sessionImplementor, Object o) throws HibernateException {
        long val = sr.nextInt();
        return Math.abs(val);
    }
    
    public String generate() {
        return "" + generate(null, null);
    }
}
