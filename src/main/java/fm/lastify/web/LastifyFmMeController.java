/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fm.lastify.web;

import java.util.List;
import java.util.Map;

import org.socialsignin.provider.lastfm.LastFmProviderService;
import org.socialsignin.provider.soundcloud.SoundCloudProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.lastfm.api.LastFm;
import org.springframework.social.lastfm.api.Track;
import org.springframework.social.soundcloud.api.SoundCloud;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fm.lastify.service.LastifyFmService;

@Controller
@RequestMapping("/me")
public class LastifyFmMeController {

	@Autowired
	private LastFmProviderService lastFmProviderService;
	
	@Autowired
	private SoundCloudProviderService soundCloudProviderService;
	
	private String getAuthenticatedUserName() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return authentication == null ? null : authentication.getName();
	}

	@RequestMapping("")
	public String me(Map model) {
		model.put("userName", getAuthenticatedUserName());

		// Display on the jsp which security level the page is intended for
		model.put("securityLevel", "Protected");
		
		
		LastFm lastFm = lastFmProviderService.getAuthenticatedApi();
		SoundCloud soundCloud = soundCloudProviderService.getAuthenticatedApi();

		if (lastFm != null)
		{
			String lastFmUserName = lastFm.userOperations().getUserProfile().getName();
			model.put("lovedTracks",lastFm.userOperations().getLovedTracks(lastFmUserName));
			model.put("topTracks",lastFm.userOperations().getTopTracks(lastFmUserName));
			//model.put("recentTracks",lastFm.userOperations().getRecentTracks(lastFmUserName));
		}
		if (soundCloud != null)
		{
			model.put("soundCloudFavorites", soundCloud.meOperations().getFavorites());
		}
		
		

		return "helloWorld";
	}
	
	

}
