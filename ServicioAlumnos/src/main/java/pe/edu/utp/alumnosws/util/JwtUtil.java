package pe.edu.utp.alumnosws.util;

import java.security.Key;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private static String secret = "/MRWAx+mKR3lE6X/ZS5K8SUux+vK3MynQbiFvUEzAOf4uQOcuUcUxLxWRhjITxih";
	   public Claims verify(String authorization) throws Exception {

	        try {
	            Claims claims = Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(authorization).getBody();
	            return claims;
	        } catch(Exception e) {
	            throw new AccessDeniedException("Access Denied");
	        }

	    }
	   public class AccessDeniedException extends RuntimeException{
		    
		    public AccessDeniedException(String message){
		        super(message);
		    }
	   } 
		private Key getSignInKey() {
			// TODO Auto-generated method stub
			byte[] KeyBytes = Decoders.BASE64.decode(secret);
			return Keys.hmacShaKeyFor(KeyBytes);
		}
}
