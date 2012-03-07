package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import entity.Tile;

public class TileEditionPan extends JPanel {

	private CornerHeightSlide sliderHeight;
	private CornerHeightSlide sliderHeightToDraw;

	private JPanel texturePanel;
	private JComboBox textureCombo;
	private JLabel textureLabel;

	private JPanel typePanel;
	private JComboBox typeCombo;
	private JLabel typeLabel;

	private JPanel decorationPanel;
	private JComboBox decorationCombo;
	private JLabel decorationLabel;

	private JPanel idPanel;
	private JLabel idLabel;

	private Tile currentTile;

	private Editor containerFrame;

	private boolean saveable;

	private OnSlideEvent slideEvent;
	private ComboSelectEvent comboSelectEvent;

	public TileEditionPan(Editor containerFrame) {
		CreateElements();
		PreparePanels();
		AddElements();
		CheckSaveButton();

		this.setContainerFrame(containerFrame);
		this.addKeyListener(new KeyShortcut());
	}

	public void Reset() {
		currentTile = null;
		sliderHeight.Reset();
		sliderHeightToDraw.Reset();
		textureCombo.setSelectedIndex(0);
		typeCombo.setSelectedIndex(0);
		decorationCombo.setSelectedIndex(0);
		idLabel.setText("X/Y");
		CheckSaveButton();
	}

	public void CheckSaveButton() {
		if (currentTile != null) {
			setSaveable(true);
		} else {
			setSaveable(false);
		}
	}

	public void CreateElements() {
		slideEvent = new OnSlideEvent();
		comboSelectEvent = new ComboSelectEvent();

		sliderHeight = new CornerHeightSlide("Height");
		sliderHeightToDraw = new CornerHeightSlide("Height to Draw");

		texturePanel = new JPanel();
		textureCombo = new JComboBox();
		textureLabel = new JLabel("Texture :");

		typePanel = new JPanel();
		typeCombo = new JComboBox();
		typeLabel = new JLabel("Type :");

		decorationPanel = new JPanel();
		decorationCombo = new JComboBox();
		decorationLabel = new JLabel("Decoration :");

		idPanel = new JPanel();
		idLabel = new JLabel();
	}

	private void PreparePanels() {
		PrepareTexturePanel();
		PrepareTypePanel();
		PrepareDecorationPanel();
		PrepareIdPanel();
		ActivateActions();
	}

	private void ActivateActions() {
		sliderHeight.slide.addChangeListener(slideEvent);
		sliderHeightToDraw.slide.addChangeListener(slideEvent);
		textureCombo.addActionListener(comboSelectEvent);
		typeCombo.addActionListener(comboSelectEvent);
		decorationCombo.addActionListener(comboSelectEvent);
	}

	private void DeactivateActions() {
		sliderHeight.slide.removeChangeListener(slideEvent);
		sliderHeightToDraw.slide.removeChangeListener(slideEvent);
		textureCombo.removeActionListener(comboSelectEvent);
		typeCombo.removeActionListener(comboSelectEvent);
		decorationCombo.removeActionListener(comboSelectEvent);
	}

	private void PrepareTexturePanel() {
		Tile.textureType[] textureArray = Tile.textureType.values();
		for (int i = 0; i < textureArray.length; i++) {
			textureCombo.addItem(textureArray[i].toString());
		}
		texturePanel.add(textureLabel);
		texturePanel.add(textureCombo);
		texturePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

	}

	private void PrepareTypePanel() {
		Tile.tileType[] tileTypeArray = Tile.tileType.values();
		for (int i = 0; i < tileTypeArray.length; i++) {
			typeCombo.addItem(tileTypeArray[i].toString());
		}
		typePanel.add(typeLabel);
		typePanel.add(typeCombo);
		typePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}

	private void PrepareDecorationPanel() {
		Tile.decorationType[] decorationArray = Tile.decorationType.values();
		for (int i = 0; i < decorationArray.length; i++) {
			decorationCombo.addItem(decorationArray[i].toString());
		}
		decorationPanel.add(decorationLabel);
		decorationPanel.add(decorationCombo);
		decorationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}

	private void PrepareIdPanel() {
		idLabel.setText("X/Y");
		idPanel.add(idLabel, BorderLayout.CENTER);
		idPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}

	public void AddElements() {
		this.setLayout(new GridLayout(6, 1));

		this.add(sliderHeight);
		this.add(sliderHeightToDraw);

		this.add(texturePanel);
		this.add(typePanel);
		this.add(decorationPanel);
		this.add(idPanel);
	}

	public void LoadTile(Tile tile) {
		this.currentTile = tile;

		DeactivateActions();

		this.sliderHeight.setTheValueTo(tile.getHeight());
		this.sliderHeightToDraw.setTheValueTo(tile.getHeightToDraw());

		this.textureCombo.setSelectedItem(tile.getTexture().toString());
		this.typeCombo.setSelectedItem(tile.getType().toString());
		this.decorationCombo.setSelectedItem(tile.getDecoration().toString());

		ActivateActions();

		this.idLabel.setText(tile.getPosX() + "/" + tile.getPosY());
		CheckSaveButton();
	}

	public void SaveTile() {
		if (currentTile.getHeight() != this.sliderHeight.slide.getValue() || currentTile.getHeightToDraw() != this.sliderHeightToDraw.slide.getValue()
				|| currentTile.getTexture() != Tile.textureType.valueOf(this.textureCombo.getSelectedItem().toString())
				|| currentTile.getType() != Tile.tileType.valueOf(this.typeCombo.getSelectedItem().toString())
				|| currentTile.getDecoration() != Tile.decorationType.valueOf(this.decorationCombo.getSelectedItem().toString())) {

			currentTile.setHeight(this.sliderHeight.slide.getValue());
			currentTile.setHeightToDraw(this.sliderHeightToDraw.slide.getValue());

			currentTile.setTexture(Tile.textureType.valueOf(this.textureCombo.getSelectedItem().toString()));
			currentTile.setType(Tile.tileType.valueOf(this.typeCombo.getSelectedItem().toString()));
			currentTile.setDecoration(Tile.decorationType.valueOf(this.decorationCombo.getSelectedItem().toString()));
			containerFrame.updateCancas();
		}
	}

	public Editor getContainerFrame() {
		return containerFrame;
	}

	public void setContainerFrame(Editor containerFrame) {
		this.containerFrame = containerFrame;
	}

	public boolean isSaveable() {
		return saveable;
	}

	public void setSaveable(boolean saveable) {
		this.saveable = saveable;
	}

	public class KeyShortcut implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if (currentTile != null) {
				switch (e.getKeyChar()) {
				case 'a':
					typeCombo.setSelectedItem(Tile.tileType.Walkable.toString());
					SaveTile();
					break;
				case 'z':
					typeCombo.setSelectedItem(Tile.tileType.Uncrossable.toString());
					SaveTile();
					break;
				case 'e':
					typeCombo.setSelectedItem(Tile.tileType.DifficultGround.toString());
					SaveTile();
					break;

				case 'q':
					textureCombo.setSelectedItem(Tile.textureType.Grass.toString());
					SaveTile();
					break;
				case 's':
					textureCombo.setSelectedItem(Tile.textureType.Earth.toString());
					SaveTile();
					break;
				case 'd':
					textureCombo.setSelectedItem(Tile.textureType.Sand.toString());
					SaveTile();
					break;
				case 'f':
					textureCombo.setSelectedItem(Tile.textureType.Stone.toString());
					SaveTile();
					break;

				default:
					switch (e.getKeyCode()) {
					// arrowup
					case 38:
						SaveTile();
						if (currentTile.getPosX() - 1 >= 0) {
							LoadTile(containerFrame.getMap().getTile(currentTile.getPosX() - 1, currentTile.getPosY()));
						}
						if (isFocusable()) {
							requestFocusInWindow();
						}
						containerFrame.setCanvasFocusOn(currentTile.getPosX(), currentTile.getPosY());
						break;
					// arrowleft
					case 37:
						SaveTile();
						if (currentTile.getPosY() - 1 >= 0) {
							LoadTile(containerFrame.getMap().getTile(currentTile.getPosX(), currentTile.getPosY() - 1));
						}
						if (isFocusable()) {
							requestFocusInWindow();
						}
						containerFrame.setCanvasFocusOn(currentTile.getPosX(), currentTile.getPosY());
						break;
					// arrowrigth
					case 39:
						SaveTile();
						if (currentTile.getPosY() < containerFrame.getMap().getWidth() - 1) {
							LoadTile(containerFrame.getMap().getTile(currentTile.getPosX(), currentTile.getPosY() + 1));
						}
						if (isFocusable()) {
							requestFocusInWindow();
						}
						containerFrame.setCanvasFocusOn(currentTile.getPosX(), currentTile.getPosY());
						break;
					// arrowdown
					case 40:
						SaveTile();
						if (currentTile.getPosX() < containerFrame.getMap().getLength() - 1) {
							LoadTile(containerFrame.getMap().getTile(currentTile.getPosX() + 1, currentTile.getPosY()));
						}
						if (isFocusable()) {
							requestFocusInWindow();
						}
						containerFrame.setCanvasFocusOn(currentTile.getPosX(), currentTile.getPosY());
						break;
					default:
						System.out.println(e.getKeyCode());
						break;
					}
				}
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

		@Override
		public void keyTyped(KeyEvent e) {

		}
	}

	public class OnSlideEvent implements ChangeListener {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			SaveTile();
		}

	}

	public class ComboSelectEvent implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SaveTile();
		}
	}

}