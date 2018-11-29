package ch.epfl.sweng.graded.grading;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public final class JUnitGradeSheetTestRunner extends BlockJUnit4ClassRunner {
    private static final JUnitGradeSheetListener listener = new JUnitGradeSheetListener();

    public JUnitGradeSheetTestRunner(final Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(final RunNotifier notifier) {
        listener.testRunStarted(getDescription());

        notifier.addListener(listener);

        super.run(notifier);

        notifier.removeListener(listener);

        listener.testRunFinished(null);
    }
}
