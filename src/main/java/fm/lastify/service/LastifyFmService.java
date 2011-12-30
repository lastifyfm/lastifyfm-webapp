package fm.lastify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.lastfm.api.LastFm;
import org.springframework.social.lastfm.api.impl.LastFmTemplate;
import org.springframework.social.soundcloud.api.SoundCloud;
import org.springframework.social.soundcloud.api.impl.SoundCloudTemplate;

import org.springframework.stereotype.Service;

@Service
public class LastifyFmService {


    @Value("${soundcloud.consumerKey}")
    private String soundCloudClientId;
    
    @Value("${lastfm.consumerKey}")
    private String lastFmApiKey;
	
	
	@Autowired
	private UsersConnectionRepository usersConnectionRepository;
	
	protected String getAuthenticatedUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth == null || auth.getName().equals("anonymousUser") ? null :auth.getName();
	}
	
	private <T >Connection<T> getConnection(Class<T> clazz)
	{
		try
		{
			if (getAuthenticatedUserId() != null)
			{
				List<Connection<T>> connections = usersConnectionRepository.createConnectionRepository(getAuthenticatedUserId()).findConnections(clazz);
				if (connections != null && connections.size() > 0) return connections.get(0);
			}
			return null;
		}
		catch (NotConnectedException exception)
		{
			return null;
		}
	}
	
	public <T> T getAuthenticatedApi(Class<T> clazz)
	{
		Connection<T> connection = getConnection(clazz);
		if (connection != null)
		{
			return connection.getApi();
		}
		else
		{
			return null;
		}
	}

	public SoundCloud getSoundCloudApi()
	{

		SoundCloud soundCloud = getAuthenticatedApi(SoundCloud.class);
		if (soundCloud != null)
		{
			return soundCloud;
		}
		else
		{
			return new SoundCloudTemplate(soundCloudClientId);
		}
	}
	
	public LastFm getLastFmApi()
	{

		LastFm lastFm = getAuthenticatedApi(LastFm.class);
		if (lastFm != null)
		{
			return lastFm;
		}
		else
		{
			return new LastFmTemplate("spring-social-lastfm/1.0.1-SNAPSHOT",lastFmApiKey);	
		}
	}
	
	
	
	
}