package org.givwenzen;

import org.givwenzen.annotations.InstantiationStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class GivWenZenExecutorCreatorTest {
    private GivWenZenExecutorCreator creator;

    @Before
    public void setup() {
        creator = GivWenZenExecutorCreator.instance();
    }

    @Test
    public void shouldCreateDefaultInstanceNeatly() throws Exception {
        GivWenZenExecutor executor = creator.create();
        assertThat(executor.customParserFinder).isNotNull();
        assertThat(executor.domainStepFactory).isNotNull();
        assertThat(executor.customParserFinder).isNotNull();
        assertThat(executor.stepState).isNotNull();
        assertThat(executor.methodLocator).isNotNull();
    }

    @Test
    public void shouldBeAbleToCreateGivWenZenExecutorWithDifferentStepPackage() throws Exception {
        String basePackage = "test.package.name.";
        GivWenZenExecutor executor = creator
                .stepClassBasePackage(basePackage)
                .create();
        assertThat(((DomainStepFinder) executor.domainStepFinder).getPackage()).isEqualTo(basePackage);
    }

    @Test
    public void shouldBeAbleToCreateGivWenZenExecutorWithStepSharedStateObject() throws Exception {
        String state = "my state object";
        GivWenZenExecutor executor = creator
                .customStepState(state)
                .create();
        assertThat(executor.getCustomStepState()[0]).isEqualTo(state);
    }

    @Test
    public void shouldBeAbleToCreateGivWenZenExecutorWithCustomStepClassInstantiationStrategy() throws Exception {
        InstantiationStrategy instantiationStrategy = mock(InstantiationStrategy.class);
        GivWenZenExecutor executor = creator
                .customInstantiationStrategies(instantiationStrategy)
                .create();

        List<InstantiationStrategy> strategies = ((DomainStepFactory) executor.domainStepFactory).getInstantiationStrategies();
        assertThat(strategies).contains(instantiationStrategy);
    }

    @Test
    public void shouldCreateInstance() throws Exception {
        IDomainStepFactory factory = mock(IDomainStepFactory.class);
        IDomainStepFinder finder = mock(IDomainStepFinder.class);
        ICustomParserFinder parserFinder = mock(ICustomParserFinder.class);

        GivWenZenExecutor executor = creator
                .domainStepFactory(factory)
                .domainStepFinder(finder)
                .customStepState("foo")
                .customParserFinder(parserFinder)
                .create();

        assertThat(executor.domainStepFactory).isEqualTo(factory);
        assertThat(executor.domainStepFinder).isEqualTo(finder);
        assertThat(executor.stepState).isEqualTo(new Object[]{"foo"});
        assertThat(executor.customParserFinder).isEqualTo(parserFinder);
    }
}