import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SpinnerNumberModel;


public class ServerGui {

	protected JFrame frmDarknetServer;

	/**
	 * Create the application.
	 */
	public ServerGui(int port, Route route, ConnectionList connections, 
			LinkList links, MessageList messages) {
		initialize(port, route, connections, links, messages);
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize(int port, Route route, 
			final ConnectionList connections, 
			final LinkList links,
			MessageList messages) {
		frmDarknetServer = new JFrame();
		frmDarknetServer.setTitle("DarkNet Server");
		frmDarknetServer.setBounds(100, 100, 800, 600);
		frmDarknetServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDarknetServer.getContentPane().setLayout(null);
		
		JScrollPane scrollPaneNodes = new JScrollPane();
		scrollPaneNodes.setBounds(12, 12, 180, 374);
		frmDarknetServer.getContentPane().add(scrollPaneNodes);
		
		final JList<Connection> listNodes = new JList<Connection>(connections);
		scrollPaneNodes.setViewportView(listNodes);
		
		JScrollPane scrollPaneLinks = new JScrollPane();
		scrollPaneLinks.setBounds(204, 12, 180, 374);
		frmDarknetServer.getContentPane().add(scrollPaneLinks);
		
		final JList<String> listLinks = new JList<String>(links);
		scrollPaneLinks.setViewportView(listLinks);
		
		JScrollPane scrollPanePackets = new JScrollPane();
		scrollPanePackets.setBounds(396, 12, 386, 549);
		frmDarknetServer.getContentPane().add(scrollPanePackets);
		
		JList<String> listPackets = new JList<String>(messages);
		scrollPanePackets.setViewportView(listPackets);
		
		JButton btnCircular = new JButton("Create Circular links");
		btnCircular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] selected = listNodes.getSelectedIndices();
				if(selected.length > 2){
					int nodeA = connections.get(selected[0]).node;
					int nodeB = connections.get(selected[selected.length-1]).node;
					System.out.println("Create link between "+nodeA+" and "+nodeB);
					String id = connections.get(selected[0]).id;
					if(id.equals(connections.get(selected[selected.length-1]).id)){
						links.addElement(id + (char) 13 + nodeA + (char) 13 + nodeB);
					}
					for(int i = 0; i < selected.length - 1; i++){
						int node1 = connections.get(selected[i]).node;
						int node2 = connections.get(selected[i+1]).node;
						System.out.println("Create link between "+node1+" and "+node2);
						String idl = connections.get(selected[i]).id;
						if(id.equals(connections.get(selected[i]).id)){
							links.addElement(idl + (char) 13 + node1 + (char) 13 + node2);
						}
					}
					
				}
			}
		});
		btnCircular.setBounds(12, 398, 180, 25);
		frmDarknetServer.getContentPane().add(btnCircular);
		
		JButton btnDeleteLink = new JButton("Delete Link");
		btnDeleteLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Delete link");
				int[] selected = listLinks.getSelectedIndices();
				for(int i = selected.length -1; i >=0; i--) {
					links.remove(selected[i]);
				}
			}
		});
		btnDeleteLink.setBounds(12, 469, 180, 25);
		frmDarknetServer.getContentPane().add(btnDeleteLink);
		
		JSlider sliderFailure = new JSlider();
		sliderFailure.setValue(33);
		sliderFailure.setBounds(200, 435, 184, 25);
		frmDarknetServer.getContentPane().add(sliderFailure);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		spinner.setBounds(340, 472, 44, 20);
		frmDarknetServer.getContentPane().add(spinner);
		
		JLabel lblRandomDelay = new JLabel("Random delay (s):");
		lblRandomDelay.setBounds(204, 474, 118, 15);
		frmDarknetServer.getContentPane().add(lblRandomDelay);
		
		JLabel lblDropRate = new JLabel("Drop rate:");
		lblDropRate.setBounds(204, 403, 118, 15);
		frmDarknetServer.getContentPane().add(lblDropRate);
		
		JButton btnCreateLink = new JButton("Create link");
		btnCreateLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selected = listNodes.getSelectedIndices();
				if(selected.length == 2){
					int nodeA = connections.get(selected[0]).node;
					int nodeB = connections.get(selected[1]).node;
					System.out.println("Create link between "+nodeA+" and "+nodeB);
					String id = connections.get(selected[1]).id;
					if(id.equals(connections.get(selected[0]).id)){
						links.addElement(id + (char) 13 + nodeA + (char) 13 + nodeB);
					}
				}
			}
		});
		
		btnCreateLink.setBounds(12, 432, 180, 25);
		frmDarknetServer.getContentPane().add(btnCreateLink);
		
		JCheckBox chckbxWhoisOnly = new JCheckBox("Whois only");
		chckbxWhoisOnly.setBounds(204, 497, 180, 23);
		frmDarknetServer.getContentPane().add(chckbxWhoisOnly);
		
		JCheckBox chckbxNodeCorruption = new JCheckBox("Content corruption");
		chckbxNodeCorruption.setBounds(204, 524, 160, 23);
		frmDarknetServer.getContentPane().add(chckbxNodeCorruption);
		
		JLabel labelDrop = new JLabel("0 %");
		labelDrop.setHorizontalAlignment(SwingConstants.RIGHT);
		labelDrop.setBounds(294, 403, 90, 15);
		frmDarknetServer.getContentPane().add(labelDrop);
	}
}