package cashmeataxi;

/**
 * Main class for the command-line application.
 */
public class Main {

	/**
	 * The args passed to main must follow the rules specified in the ControllerForCmdLine
	 * specifications.
	 * @param args
	 */
	public static void main(String[] args) {
		ControllerForCmdLine controller = new ControllerForCmdLine();
		controller.run(args);
	}
}
