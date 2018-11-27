package ru.naumen.sd40.log.parser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import ru.naumen.sd40.log.parser.Gc.GcTimeParser;
import ru.naumen.sd40.log.parser.Sdng.SdngTimeParser;
import ru.naumen.sd40.log.parser.Top.TopTimeParser;

@Configuration
public class LogParserConfiguration {
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SdngTimeParser sdngTimeParser() {
        return new SdngTimeParser();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public GcTimeParser gcTimeParser() {
        return new GcTimeParser();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public TopTimeParser topTimeParser() {
        return new TopTimeParser();
    }
}
