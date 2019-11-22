package ex11.views;

/**
 * Describes a view interface
 */
public interface View {
    /**
     * Generate HTML output of the concrete view
     * @return HTML String of the page
     */
    public String render();
}