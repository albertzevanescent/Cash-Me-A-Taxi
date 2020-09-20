package cashmeataxi;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Main class for the GUI application.
 */
public class GUI extends JFrame {

	private static JTextField input;	// input textbox
	private static JLabel output; 		// output label
	private static JMenuBar menu;		// menu bar
	private static ControllerForGUI controller;	// controller for GUI object

	/**
	 * Create the GUI with a menu bar at the top, the input text at the bottom, and
	 * the output label between them.
	 * When a menu option is selected, set the appropriate option and display the
	 * prompt.
	 * Run the appropriate function and save the results to a file.
	 */
	public GUI() {
		controller = new ControllerForGUI();
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout(0, 0));

		menu = new JMenuBar(); // Functions to run
		JMenu options = new JMenu("Functions");
		menu.add(options);
		JMenuItem OpPath = new JMenuItem("Optimal Path");
		JMenuItem FZ = new JMenuItem("Find Zone");
		JMenuItem SE = new JMenuItem("Sort Edges");
		JMenuItem BG = new JMenuItem("Build Graph");

		options.add(BG);
		options.add(OpPath);
		options.add(FZ);
		options.add(SE);

		add(menu, BorderLayout.NORTH);

		input = new JTextField(10); // Input text and output display
		add(input, BorderLayout.SOUTH);
		output = new JLabel("");
		output.setHorizontalAlignment(SwingConstants.LEFT);
		output.setVerticalAlignment(SwingConstants.TOP);
		add(output, BorderLayout.CENTER);
		setOutput(controller.getMsg());

		input.addActionListener(new ActionListener() { // Entering text action
			@Override
			public void actionPerformed(ActionEvent evt) {
				String in = null, text = null;

				try {
					in = input.getText();
					switch(controller.state()) {
					case PROMPT_CRITERIA:
						if (in.isBlank()) throw new IllegalArgumentException("Invalid input.");
						int option = Integer.parseInt(in.trim());
						validateSortOption(option);
						setOutput(controller.getMsg() + "\n\nProcessing...");
						String result = controller.sortEdges(option);
						setOutput(controller.getMsg() + "\n\nSorted result saved in " + result + "\n" +
						 "\nSelect next function.");
						controller.setState(ControllerForGUI.State.PROMPT_FUNCTION);
						return;
					case PROMPT_EXIST_DATAFILE:
						controller.setGraphDataFilename(in);
						if (in.isBlank()) {
							controller.setState(ControllerForGUI.State.PROMPT_INPUT_FILENAME);
							setOutput(controller.getMsg());
						} else {
							switch(controller.function()) {
							case BUILD_GRAPH:
								setOutput(controller.getMsg() + "\nBuilding graph...\n");
								controller.buildGraph(false);
								setOutput(controller.getMsg() + "\n\nGraph data saved to " +
										controller.graphDataFile() + "\n\n" + "Select next function.");
								controller.setState(ControllerForGUI.State.PROMPT_FUNCTION);
								break;
							case OPTIMAL_PATH:
								controller.setState(ControllerForGUI.State.PROMPT_OPTPATH_ARGS);
								setOutput(controller.getMsg());
								break;
							case FIND_ZONE:
								controller.setState(ControllerForGUI.State.PROMPT_FZONE_ARGS);
								setOutput(controller.getMsg());
								break;
							case SORT_EDGES:
								controller.setState(ControllerForGUI.State.PROMPT_CRITERIA);
								setOutput(controller.getMsg());
							default:
								break;
							}
						}
						break;
					case PROMPT_INPUT_FILENAME:
						controller.setInputFilename(in);
						switch(controller.function()) {
						case BUILD_GRAPH:
							setOutput(controller.getMsg() + "\n\nBuilding graph...\n");
							controller.buildGraph(true);
							setOutput(controller.getMsg() + "\n\nGraph data saved to " +
									controller.graphDataFile() + "\n\nSelect next function.");
							controller.setState(ControllerForGUI.State.PROMPT_FUNCTION);
							break;
						case OPTIMAL_PATH:
							controller.setState(ControllerForGUI.State.PROMPT_OPTPATH_ARGS);
							setOutput(controller.getMsg());
							break;
						case FIND_ZONE:
							controller.setState(ControllerForGUI.State.PROMPT_FZONE_ARGS);
							setOutput(controller.getMsg());
							break;
						case SORT_EDGES:
							controller.setState(ControllerForGUI.State.PROMPT_CRITERIA);
							setOutput(controller.getMsg());
						default:
							break;
						}
						break;
					case PROMPT_OPTPATH_ARGS:
						String[] zones = in.trim().split(" ");
						if (zones.length != 2) throw new IllegalArgumentException("Invalid zones.");
						int zone1 = Integer.parseInt(zones[0].trim());
						int zone2 = Integer.parseInt(zones[1].trim());
						validateZone(zone1);
						validateZone(zone2);
						setOutput(controller.getMsg() + "\n\nComputing...");
						String outFile = controller.findOptimalPath(zone1, zone2);
						setOutput(controller.getMsg() + "\n\nOutput written in " + outFile + "\n"
								+ "\nSelect next function.");
						controller.setState(ControllerForGUI.State.PROMPT_FUNCTION);
						break;
					case PROMPT_FZONE_ARGS:
						int zone = Integer.parseInt(in.trim());
						validateZone(zone);
						setOutput(controller.getMsg() + "\n\nComputing...");
						outFile = controller.findZone(zone);
						setOutput(controller.getMsg() + "\n\nOutput written in " + outFile + "\n"
								+ "\nSelect next function.");
						controller.setState(ControllerForGUI.State.PROMPT_FUNCTION);
						break;
					default:
						break;
					}
				} catch (IllegalArgumentException e) {
					text = "<html>Invalid Input:<br/>" + in + "<br>" +
							e.getMessage() + "<br>" +
							"Enter next function to run.</html>";
					input.setText("");
					output.setText(text);
				} 
				
				input.setText("");
			}
		});
		
		// Menu buttons
		OpPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				controller.setFunction(ControllerForGUI.Function.OPTIMAL_PATH);
				setOutput(controller.getMsg());
			}
		});

		FZ.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				controller.setFunction(ControllerForGUI.Function.FIND_ZONE);
				setOutput(controller.getMsg());
			}
		});
		
		SE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				controller.setFunction(ControllerForGUI.Function.SORT_EDGES);
				setOutput(controller.getMsg());
			}
		});
		
		BG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				controller.setFunction(ControllerForGUI.Function.BUILD_GRAPH);
				setOutput(controller.getMsg());
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Cash Me A Taxi");
		setSize(800, 600);
		setVisible(true);
	}
	
	/**
	 * The main function to run when running the GUI application.
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GUI();
			}
		});
	}
	
	/**
	 * Set the text of output line by line.
	 * @param text
	 */
	private static void setOutput(String text) {
		String[] s = text.split("\n");
		String out = "<html>";
		for(String i : s) {
			out += i + "<br/>";
		}
		out += "</html>";
		output.setText(out);
		
	}
	
	/**
	 * Validates the zone ID inputted by the user. The zone must be between 1 and 263,
	 * which is the range of valid NYC taxi zones.
	 * Throws a new IllegalArgumentException if the zone is not valid. The calling
	 * function then catches the exception and displays an error message on the GUI,
	 * and prompts the user to select a new function.
	 * @param zone
	 * @throws IllegalArgumentException if the zone is invalid.
	 */
	private static void validateZone(int zone) {
		if (zone < 1 || zone > 263) throw new IllegalArgumentException("Invalid zone (must be between 1 - 263).");
	}
	
	/**
	 * Validates the sorting criteria option inputted by the user. The option must be
	 * between 1 and 5.
	 * Throws a new IllegalArgumentException if the option is not valid. The calling
	 * function then catches the exception and displays an error message on the GUI,
	 * and prompts the user to select a new function.
	 * @param option
	 * @throws IllegalArgumentException if the zone is invalid.
	 */
	private static void validateSortOption(int option) {
		if (option < 1 || option > 5) throw new IllegalArgumentException("Invalid option.");
	}
}
