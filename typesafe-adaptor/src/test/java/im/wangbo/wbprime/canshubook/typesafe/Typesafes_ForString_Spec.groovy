package im.wangbo.wbprime.canshubook.typesafe

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import im.wangbo.wbprime.canshubook.Visitors
import spock.lang.Specification
import spock.lang.Unroll

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
class Typesafes_ForString_Spec extends Specification {
    SampleData sampleData

    Config config;

    void setup() {
        sampleData = SampleData.randomize()

        config = ConfigFactory.parseReader(new StringReader(sampleData.toJson()))
    }

    def "test get empty string as string"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getEmptyStringKey(), Visitors.forString())
        then:
        opt.isPresent()

        def v = opt.get()
        v.isEmpty()
    }

    @Unroll
    def "test get string as string repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getStringKey(), Visitors.forString())
        then:
        opt.isPresent()

        def v = opt.get()
        v == sampleData.getStringVal()
        where:
        i << (1..100)
    }

    @Unroll
    def "test get int as string repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getIntKey(), Visitors.forString())
        then:
        opt.isPresent()

        def v = opt.get()
        v == String.valueOf(sampleData.getIntVal())
        where:
        i << (1..100)
    }

    @Unroll
    def "test get long as string repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getLongKey(), Visitors.forString())
        then:
        opt.isPresent()

        def v = opt.get()
        v == String.valueOf(sampleData.getLongVal())

        where:
        i << (1..100)
    }

    @Unroll
    def "test get float as string repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getFloatKey(), Visitors.forString())
        then:
        opt.isPresent()

        def v = opt.get()
        v == String.valueOf(sampleData.getFloatVal())

        where:
        i << (1..100)
    }

    @Unroll
    def "test get bool as string repeated #i"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getBoolKey(), Visitors.forString())
        then:
        opt.isPresent()

        def v = opt.get()
        v == String.valueOf(sampleData.getBoolVal())

        where:
        i << (1..10)
    }

    def "test get list as string"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getListKey(), Visitors.forString())
        then:
        !opt.isPresent()
    }

    def "test get map as string"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getMapKey(), Visitors.forString())
        then:
        !opt.isPresent()
    }

    def "test get absent value as string"() {
        when:
        def opt = Typesafes.asOptional(config, sampleData.getAbsentKey(), Visitors.forString())
        then:
        !opt.isPresent()
    }
}
