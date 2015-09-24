package no.cantara.dwsample.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import no.cantara.dwsample.api.Planet;
import no.cantara.dwsample.api.Saying;
import no.cantara.dwsample.domain.counter.CounterService;
import org.constretto.annotation.Configuration;
import org.constretto.annotation.Configure;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldResource implements no.cantara.dwsample.api.HelloWorldResource {
    private final String template;
    private final String defaultName;

    private final CounterService counterService;

    @Configure
    public HelloWorldResource(@Configuration String template, @Configuration String defaultName, CounterService counterService) {
        this.template = template;
        this.defaultName = defaultName;
        this.counterService = counterService;
    }

    @Timed
    public Saying hello(Optional<String> name) {
        final String value = String.format(template, name.or(defaultName));
        return new Saying(counterService.next(), value);
    }

    @Timed
    public Saying hello(Planet planet) {
        final String value = "Hello " + planet.getYourName() + " on planet " + planet.getPlanetName();
        return new Saying(counterService.next(), value);
    }
}