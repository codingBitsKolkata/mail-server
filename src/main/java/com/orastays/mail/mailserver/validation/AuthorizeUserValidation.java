package com.orastays.mail.mailserver.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class AuthorizeUserValidation {

	private static final Logger logger = LogManager.getLogger(AuthorizeUserValidation.class);
	
	/*@Autowired
	protected MessageUtil messageUtil;
	
	@Autowired
	protected SignUpService signUpService;
	
	@Autowired
	protected CountryDAO countryDAO;
	
	@Autowired
	protected HttpServletRequest request;
	
	public UserModel getUserDetails(String authorization, Principal user) throws FormExceptions {

		if (logger.isInfoEnabled()) {
			logger.info("getUserDetails -- START");
		}
		
		Map<String, Exception> exceptions = new LinkedHashMap<>();
		UserModel userModel = new UserModel();
		String jwtBody = authorization.split(" ")[1];
        String[] splitString = jwtBody.split("\\.");
        String base64EncodedBody = splitString[1];
        Base64 base64Url = new Base64(true);


        String body = new String(base64Url.decode(base64EncodedBody));
        Map<String, Object> retMap = new Gson().fromJson(
                  body, new TypeToken<HashMap<String, Object>>() {}.getType()
              );
        
        userModel.setUserId(String.valueOf(retMap.get("userId")));
        userModel.setEmailId(String.valueOf(retMap.get("emailId")));
        List<String> roles = (List<String>) retMap.get("authorities");
        userModel.setRole(roles.get(0));
		if (logger.isInfoEnabled()) {
			logger.info("userModel ==>> "+userModel);
		}
		if(Util.isEmpty(userModel.getUserId())) {
			exceptions.put(messageUtil.getBundle("user.invalid.code"), new Exception(messageUtil.getBundle("user.invalid.message")));
		}
		
		if (exceptions.size() > 0)
			throw new FormExceptions(exceptions);
		
		if (logger.isInfoEnabled()) {
			logger.info("getUserDetails -- END");
		}
		
		return userModel;
	}*/
}
