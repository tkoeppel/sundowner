import de.tkoeppel.sundowner.configuration.SpringMvcConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfig() : WebMvcConfigurer {
	@Autowired
	private lateinit var springMvcConfig: SpringMvcConfig

	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping(springMvcConfig.mapping)//
			.allowedOrigins(springMvcConfig.allowedOrigins) //
			.allowedMethods(springMvcConfig.allowedMethods) //
			.allowedHeaders(springMvcConfig.allowedHeaders) //
			.allowCredentials(springMvcConfig.allowCredentials) //
			.maxAge(springMvcConfig.maxAge)
	}
}