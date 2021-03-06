package im.wangbo.wbprime.canshubook


import spock.lang.Shared
import spock.lang.Specification

/**
 * TODO add description here.
 *
 * @author Elvis Wang
 * @since 1.0.0
 */
public class Visitors_ForBigDecimal_Spec extends Specification {
    @Shared
    Visitor<BigDecimal> visitor = Visitors.forBigDecimal()

    def "test from null"() {
        given:
        Acceptor acceptor = Acceptor.ofNull()
        when:
        def opt = acceptor.accept(visitor)
        then:
        !opt.isPresent()
    }

    def "test from boolean"() {
        given:
        Acceptor acceptor = Acceptor.ofBoolean(v)
        when:
        def opt = acceptor.accept(visitor)
        then:
        if (has) {
            assert opt.isPresent()
            assert opt.get() == (double) r
        } else {
            assert !opt.isPresent()
        }

        where:
        v     | has   | r
        true  | false | 0
        false | false | 0
    }

    def "test from long"() {
        given:
        Acceptor acceptor = Acceptor.ofLong(v)
        when:
        def opt = acceptor.accept(visitor)
        then:
        if (has) {
            assert opt.isPresent()
            assert opt.get() == (double) r
        } else {
            assert !opt.isPresent()
        }

        where:
        v                       | has  | r
        1                       | true | 1
        0                       | true | 0
        -1                      | true | -1
        Integer.MAX_VALUE       | true | Integer.MAX_VALUE
        Integer.MIN_VALUE       | true | Integer.MIN_VALUE
        1L + Integer.MAX_VALUE  | true | 1L + Integer.MAX_VALUE
        -1L + Integer.MIN_VALUE | true | -1L + Integer.MIN_VALUE
        Long.MAX_VALUE          | true | Long.MAX_VALUE
        Long.MIN_VALUE          | true | Long.MIN_VALUE
    }

    def "test from double"() {
        given:
        Acceptor acceptor = Acceptor.ofDouble(v)
        when:
        def opt = acceptor.accept(visitor)
        then:
        if (has) {
            assert opt.isPresent()
            assert opt.get() == (double) r
        } else {
            assert !opt.isPresent()
        }

        where:
        v                              | has   | r
        1.0                            | true  | 1
        0                              | true  | 0
        -1.0                           | true  | -1
        0.1                            | true  | 0.1
        -0.1                           | true  | -0.1
        Double.MAX_VALUE               | true  | Double.MAX_VALUE
        Double.MIN_VALUE               | true  | Double.MIN_VALUE
        Double.POSITIVE_INFINITY       | false | Double.POSITIVE_INFINITY // NOTE here
        Double.NEGATIVE_INFINITY       | false | Double.NEGATIVE_INFINITY // NOTE here
        Double.NaN                     | false | Double.NaN // NOTE here
        Double.MIN_NORMAL              | true  | Double.MIN_NORMAL
        Double.valueOf(Long.MAX_VALUE) | true  | Long.MAX_VALUE
        Double.valueOf(Long.MIN_VALUE) | true  | Long.MIN_VALUE
    }

    def "test from string"() {
        given:
        Acceptor acceptor = Acceptor.ofString(v)
        when:
        def opt = acceptor.accept(visitor)
        then:
        if (has) {
            assert opt.isPresent()
            assert opt.get() == (double) r
        } else {
            assert !opt.isPresent()
        }

        where:
        v                                        | has   | r
        ""                                       | false | 0
        "Y"                                      | false | 0
        "y"                                      | false | 0
        "N"                                      | false | 0
        "n"                                      | false | 0
        "YES"                                    | false | 0
        "yES"                                    | false | 0
        "YeS"                                    | false | 0
        "yeS"                                    | false | 0
        "YEs"                                    | false | 0
        "yEs"                                    | false | 0
        "Yes"                                    | false | 0
        "yes"                                    | false | 0
        "NO"                                     | false | 0
        "nO"                                     | false | 0
        "No"                                     | false | 0
        "no"                                     | false | 0
        "TRUE"                                   | false | 0
        "tRUE"                                   | false | 0
        "true"                                   | false | 0
        "True"                                   | false | 0
        "FALSE"                                  | false | 0
        "fALSE"                                  | false | 0
        "false"                                  | false | 0
        "False"                                  | false | 0
        String.valueOf(1)                        | true  | 1
        String.valueOf(0)                        | true  | 0
        String.valueOf(-1)                       | true  | -1
        String.valueOf(Integer.MAX_VALUE)        | true  | Integer.MAX_VALUE
        String.valueOf(Integer.MIN_VALUE)        | true  | Integer.MIN_VALUE
        String.valueOf(1L + Integer.MAX_VALUE)   | true  | 1L + Integer.MAX_VALUE
        String.valueOf(-1L + Integer.MIN_VALUE)  | true  | -1L + Integer.MIN_VALUE
        String.valueOf(Long.MAX_VALUE)           | true  | Long.MAX_VALUE
        String.valueOf(Long.MIN_VALUE)           | true  | Long.MIN_VALUE
        "1.0"                                    | true  | 1
        "0.0"                                    | true  | 0
        "-1.0"                                   | true  | -1
        "0.1"                                    | true  | 0.1
        "-0.1"                                   | true  | -0.1
        String.valueOf(Double.MAX_VALUE)         | true  | Double.MAX_VALUE
        String.valueOf(Double.MIN_VALUE)         | true  | Double.MIN_VALUE
        String.valueOf(Double.POSITIVE_INFINITY) | false | Double.POSITIVE_INFINITY // NOTE HERE
        String.valueOf(Double.NEGATIVE_INFINITY) | false | Double.NEGATIVE_INFINITY // NOTE HERE
        String.valueOf(Double.NaN)               | false | Double.NaN // NOTE HERE
        String.valueOf(Double.MIN_NORMAL)        | true  | Double.MIN_NORMAL
    }
}
