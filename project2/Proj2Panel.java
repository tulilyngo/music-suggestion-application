/**
 * Lily Ngo 
 * CSCI 3381 OO with Java
 * Project 2
 */

package project2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Proj2Panel extends JPanel {
  private int count;
  private Collection likedSongs;
  private Collection allTheSongs;
  private JTextField userInput;
  private final ButtonGroup buttonGroup = new ButtonGroup();
  private JTextField txtLongitude;
  private JComboBox comboBox;
  private JScrollPane scrollPane_1;
  private JTextArea allSongs;
  private JSpinner spinner;
  private JTextField textFieldId;
  private JTextField textFieldArtist;
  private JTextField textFieldGenre;
  private JTextField textFieldName;
  private JTextField textFieldAlbum;
  private JTextField textFieldYear;
  private JTextField textFieldLongitude;

  public Proj2Panel() {
    // Add data to collection
    String fullFile = "./project2/finalTracks.csv";
    allTheSongs = new Collection();
    allTheSongs.readFile(fullFile);

    String testFile = "./project2/testTracks.csv";
    likedSongs = new Collection();
    likedSongs.readFile(testFile);

    count = 0;
    setLayout(null);
    setBackground(SystemColor.control);
    setPreferredSize(new Dimension(698, 500));

    // Add image
    JLabel lblMusic = new JLabel("");
    lblMusic.setHorizontalAlignment(SwingConstants.RIGHT);
    lblMusic.setBounds(49, 40, 96, 96);
    add(lblMusic);
    lblMusic.setIcon(new ImageIcon(Proj2Panel.class.getResource("/project2/giphy.gif")));

    // add title
    JLabel lblMusicSuggestion = new JLabel("Music Suggestion");
    lblMusicSuggestion.setFont(new Font("Tahoma", Font.BOLD, 14));
    lblMusicSuggestion.setHorizontalAlignment(SwingConstants.RIGHT);
    lblMusicSuggestion.setBounds(160, 11, 130, 20);
    add(lblMusicSuggestion);

    // add credit
    JLabel lblByLilyNgo = new JLabel("By Lily Ngo");
    lblByLilyNgo.setFont(new Font("Tahoma", Font.ITALIC, 11));
    lblByLilyNgo.setHorizontalAlignment(SwingConstants.RIGHT);
    lblByLilyNgo.setBounds(160, 30, 130, 20);
    add(lblByLilyNgo);

    // Display all songs in the database
    // -----------------------------------------------------------

    scrollPane_1 = new JScrollPane();
    scrollPane_1.setBounds(295, 11, 400, 188);
    add(scrollPane_1);

    allSongs = new JTextArea();
    scrollPane_1.setViewportView(allSongs);

    JLabel lblAllSongs = new JLabel("All Songs");
    lblAllSongs.setHorizontalAlignment(SwingConstants.CENTER);
    lblAllSongs.setBounds(160, 104, 130, 20);
    add(lblAllSongs);

    JButton btnView = new JButton("View ");
    btnView.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String aString = "";
        Iterator<Song> iter = allTheSongs.getIterator();
        while (iter.hasNext()) {
          Song s = iter.next();
          aString += s + "\n";
        }
        allSongs.setText(aString);
      }
    });
    btnView.setBounds(160, 129, 130, 20);
    add(btnView);

    JLabel lblNumberSongs = new JLabel("");
    lblNumberSongs.setBounds(160, 179, 130, 20);
    add(lblNumberSongs);

    JCheckBox chckbxNumberOfSongs = new JCheckBox("Number of Songs");
    chckbxNumberOfSongs.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (chckbxNumberOfSongs.isSelected()) {
          lblNumberSongs.setText("Number Songs: " + allTheSongs.size());
        } else {
          lblNumberSongs.setText("");
        }
      }
    });
    chckbxNumberOfSongs.setBounds(160, 154, 130, 20);
    add(chckbxNumberOfSongs);

    // -----------------------------------------------------------

    // Song Suggestion starts here
    // -----------------------------------------------------------
    JLabel lblGenre = new JLabel("Genre: ");
    lblGenre.setBackground(SystemColor.text);
    lblGenre.setBounds(10, 210, 45, 25);
    add(lblGenre);

    comboBox = new JComboBox();
    List<String> g = Arrays.asList(allTheSongs.getGenres());
    g = new ArrayList<>(g);
    g.add("None");
    comboBox.setModel(new DefaultComboBoxModel(g.toArray()));
    comboBox.setSelectedItem("None");
    comboBox.setToolTipText(comboBox.getSelectedItem().toString());
    comboBox.setBounds(49, 210, 236, 25);
    add(comboBox);

    JLabel lblYear = new JLabel("Year:");
    lblYear.setBackground(Color.WHITE);
    lblYear.setBounds(295, 210, 45, 25);
    add(lblYear);

    spinner = new JSpinner();
    spinner.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
      }
    });
    spinner.setModel(new SpinnerListModel(
        new String[] { "None", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017" }));
    spinner.setBounds(329, 210, 125, 25);
    add(spinner);

    JButton btnGetSongs = new JButton("Get Songs");
    btnGetSongs.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Collection songs = new Collection();

        if (spinner.getValue().equals("None")) {
          songs = allTheSongs.findSongByGenre("" + comboBox.getSelectedItem());
        } else if (comboBox.getSelectedItem().equals("None")) {
          songs = allTheSongs.findSongByYear(Integer.parseInt("" + spinner.getValue()));
        } else {
          songs = allTheSongs.findSongByGenresAndYear("" + comboBox.getSelectedItem(),
              Integer.parseInt("" + spinner.getValue()));
        }
        String aString = "";
        Iterator<Song> iter = songs.getIterator();
        while (iter.hasNext()) {
          Song s = iter.next();
          aString += s + "\n";
        }
        allSongs.setText(aString);
      }
    });
    btnGetSongs.setBounds(464, 210, 231, 25);
    add(btnGetSongs);

    // -----------------------------------------------------------

    // View, Add, Edit, Remove Function start here
    // -----------------------------------------------------------
    // labels created
    JLabel lblAddTrackId = new JLabel("Track ID");
    lblAddTrackId.setBounds(10, 290, 88, 14);
    add(lblAddTrackId);

    JLabel lblAddArtistName = new JLabel("Artist Name");
    lblAddArtistName.setBounds(108, 290, 88, 14);
    add(lblAddArtistName);

    JLabel lblAddGenre = new JLabel("Genre");
    lblAddGenre.setBounds(206, 290, 88, 14);
    add(lblAddGenre);

    JLabel lblAddTrackTitle = new JLabel("Track Title");
    lblAddTrackTitle.setBounds(304, 290, 88, 14);
    add(lblAddTrackTitle);

    JLabel lblAddAlbumTitle = new JLabel("Album Title");
    lblAddAlbumTitle.setBounds(402, 290, 88, 14);
    add(lblAddAlbumTitle);

    JLabel lblAddYearCreated = new JLabel("Year Created");
    lblAddYearCreated.setBounds(500, 290, 88, 14);
    add(lblAddYearCreated);

    JLabel lblAddArtistLongitude = new JLabel("Artist Longitude");
    lblAddArtistLongitude.setBounds(598, 290, 88, 14);
    add(lblAddArtistLongitude);

    // textFields created
    textFieldId = new JTextField();
    textFieldId.setBounds(10, 305, 88, 20);
    add(textFieldId);
    textFieldId.setColumns(10);

    textFieldArtist = new JTextField();
    textFieldArtist.setColumns(10);
    textFieldArtist.setBounds(108, 305, 88, 20);
    add(textFieldArtist);

    textFieldGenre = new JTextField();
    textFieldGenre.setBounds(206, 305, 88, 20);
    add(textFieldGenre);
    textFieldGenre.setColumns(10);

    textFieldName = new JTextField();
    textFieldName.setBounds(304, 305, 88, 20);
    add(textFieldName);
    textFieldName.setColumns(10);

    textFieldAlbum = new JTextField();
    textFieldAlbum.setBounds(402, 305, 88, 20);
    add(textFieldAlbum);
    textFieldAlbum.setColumns(10);

    textFieldYear = new JTextField();
    textFieldYear.setBounds(500, 305, 88, 20);
    add(textFieldYear);
    textFieldYear.setColumns(10);

    textFieldLongitude = new JTextField();
    textFieldLongitude.setBounds(598, 305, 88, 20);
    add(textFieldLongitude);
    textFieldLongitude.setColumns(10);

    // alert implementation
    JLabel lblAlert = new JLabel();
    lblAlert.setHorizontalAlignment(SwingConstants.CENTER);
    lblAlert.setForeground(Color.RED);
    lblAlert.setBounds(10, 253, 685, 26);
    add(lblAlert);

    // view all button implementation
    JButton btnViewLikedSongs = new JButton("View Liked Songs");
    btnViewLikedSongs.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String aString = "";
        Iterator<Song> iter = likedSongs.getIterator();
        while (iter.hasNext()) {
          Song s = iter.next();
          aString += s + "\n";
        }
        allSongs.setText(aString);
        lblAlert.setText("All liked songs displayed.");
      }
    });
    btnViewLikedSongs.setBounds(10, 338, 162, 23);
    add(btnViewLikedSongs);

    // add button implementation
    JButton btnAddSong = new JButton("Add");
    btnAddSong.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        boolean addSongCondition = textFieldId.getText().isEmpty() || textFieldArtist.getText().isEmpty()
            || textFieldGenre.getText().isEmpty() || textFieldName.getText().isEmpty()
            || textFieldAlbum.getText().isEmpty() || textFieldYear.getText().isEmpty()
            || textFieldLongitude.getText().isEmpty();

        if (addSongCondition) {
          lblAlert.setText("Enter all fields to add liked song.");
        } else {
          // add song to collection
          likedSongs.add(new Song(Integer.parseInt(textFieldId.getText()), textFieldArtist.getText(),
              textFieldGenre.getText(), textFieldName.getText(), textFieldAlbum.getText(),
              Integer.parseInt(textFieldYear.getText()), Double.parseDouble(textFieldLongitude.getText())));
          lblAlert.setText("Song added to Liked Songs.");

          // display song in text area
          String aString = "";
          Iterator<Song> iter = likedSongs.getIterator();
          while (iter.hasNext()) {
            Song s = iter.next();
            aString += s + "\n";
          }
          allSongs.setText(aString);

          // Reset text fields
          textFieldId.setText("");
          textFieldArtist.setText("");
          textFieldGenre.setText("");
          textFieldName.setText("");
          textFieldAlbum.setText("");
          textFieldYear.setText("");
          textFieldLongitude.setText("");
        }
      }
    });
    btnAddSong.setBounds(182, 336, 162, 26);
    add(btnAddSong);

    // edit button implementation
    JButton btnEditSong = new JButton("Edit");
    btnEditSong.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // check condition for edit song
        boolean checkID = textFieldId.getText().isEmpty();
        boolean checkData = textFieldArtist.getText().isEmpty() && textFieldGenre.getText().isEmpty()
            && textFieldName.getText().isEmpty() && textFieldAlbum.getText().isEmpty()
            && textFieldYear.getText().isEmpty() && textFieldLongitude.getText().isEmpty();
        boolean editArtistCondition = !textFieldArtist.getText().isEmpty() && textFieldGenre.getText().isEmpty()
            && textFieldName.getText().isEmpty() && textFieldAlbum.getText().isEmpty()
            && textFieldYear.getText().isEmpty() && textFieldLongitude.getText().isEmpty();
        boolean editGenre = textFieldArtist.getText().isEmpty() && !textFieldGenre.getText().isEmpty()
            && textFieldName.getText().isEmpty() && textFieldAlbum.getText().isEmpty()
            && textFieldYear.getText().isEmpty() && textFieldLongitude.getText().isEmpty();
        boolean editName = textFieldArtist.getText().isEmpty() && textFieldGenre.getText().isEmpty()
            && !textFieldName.getText().isEmpty() && textFieldAlbum.getText().isEmpty()
            && textFieldYear.getText().isEmpty() && textFieldLongitude.getText().isEmpty();
        boolean editAlbum = textFieldArtist.getText().isEmpty() && textFieldGenre.getText().isEmpty()
            && textFieldName.getText().isEmpty() && !textFieldAlbum.getText().isEmpty()
            && textFieldYear.getText().isEmpty() && textFieldLongitude.getText().isEmpty();
        boolean editYear = textFieldArtist.getText().isEmpty() && textFieldGenre.getText().isEmpty()
            && textFieldName.getText().isEmpty() && textFieldAlbum.getText().isEmpty()
            && !textFieldYear.getText().isEmpty() && textFieldLongitude.getText().isEmpty();
        boolean editLongitude = textFieldArtist.getText().isEmpty() && textFieldGenre.getText().isEmpty()
            && textFieldName.getText().isEmpty() && textFieldAlbum.getText().isEmpty()
            && textFieldYear.getText().isEmpty() && !textFieldLongitude.getText().isEmpty();

        if (checkID) {
          lblAlert.setText("Enter Track ID to edit liked song.");
        } else {
          if (checkData) {
            lblAlert.setText("Enter a data field to edit liked song.");
          } else {
            if (editArtistCondition) {
              // edit song's artist name in Liked Songs collection
              likedSongs.modifySongbyArtist(Integer.parseInt(textFieldId.getText()), textFieldArtist.getText());
            } else if (editGenre) {
              // edit song's genre in Liked Songs collection
              likedSongs.modifySongbyGenre(Integer.parseInt(textFieldId.getText()), textFieldGenre.getText());
            } else if (editName) {
              // edit song's track title in Liked Songs collection
              likedSongs.modifySongbyName(Integer.parseInt(textFieldId.getText()), textFieldName.getText());
            } else if (editAlbum) {
              // edit song's album title in Liked Songs collection
              likedSongs.modifySongbyAlbum(Integer.parseInt(textFieldId.getText()), textFieldAlbum.getText());
            } else if (editYear) {
              // edit song's year created in Liked Songs collection
              likedSongs.modifySongbyYear(Integer.parseInt(textFieldId.getText()),
                  Integer.parseInt(textFieldYear.getText()));
            } else if (editLongitude) {
              // edit song's longitude in Liked Songs collection
              likedSongs.modifySongbyLongitude(Integer.parseInt(textFieldId.getText()),
                  Double.parseDouble(textFieldLongitude.getText()));
            } else {
              lblAlert.setText("Extra fields entered. Enter only one data field to edit liked song.");
            }

            // display if appropriate action activated
            if (editArtistCondition || editGenre || editName || editAlbum || editYear || editLongitude) {
              // display song in text area
              String aString = "";
              Iterator<Song> iter = likedSongs.getIterator();
              while (iter.hasNext()) {
                Song s = iter.next();
                aString += s + "\n";
              }
              allSongs.setText(aString);

              // Reset text fields
              textFieldId.setText("");
              textFieldArtist.setText("");
              textFieldGenre.setText("");
              textFieldName.setText("");
              textFieldAlbum.setText("");
              textFieldYear.setText("");
              textFieldLongitude.setText("");
            }

          }
        }

      }
    });
    btnEditSong.setBounds(352, 336, 162, 26);
    add(btnEditSong);

    // remove button implementation
    JButton btnRemoveSong = new JButton("Remove");
    btnRemoveSong.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        boolean checkID = textFieldId.getText().isEmpty();

        if (checkID) {
          lblAlert.setText("Enter ID to remove liked song.");
        } else {
          likedSongs.removeByID(Integer.parseInt(textFieldId.getText()));
          lblAlert.setText("Song removed.");

          // display song in text area
          String aString = "";
          Iterator<Song> iter = likedSongs.getIterator();
          while (iter.hasNext()) {
            Song s = iter.next();
            aString += s + "\n";
          }
          allSongs.setText(aString);

          // Reset text fields
          textFieldId.setText("");
          textFieldArtist.setText("");
          textFieldGenre.setText("");
          textFieldName.setText("");
          textFieldAlbum.setText("");
          textFieldYear.setText("");
          textFieldLongitude.setText("");
        }

      }
    });
    btnRemoveSong.setBounds(524, 336, 162, 26);
    add(btnRemoveSong);

    // Display images in the bottom pane
    JLabel lblClef = new JLabel("");
    lblClef.setIcon(new ImageIcon(Proj2Panel.class.getResource("/project2/trebleclef.png")));
    lblClef.setBounds(76, 372, 512, 119);
    add(lblClef);

    JLabel lblNotes = new JLabel("");
    lblNotes.setIcon(new ImageIcon(Proj2Panel.class.getResource("/project2/notes7575.png")));
    lblNotes.setBounds(209, 378, 75, 75);
    add(lblNotes);

    JLabel lblHalfNote = new JLabel("");
    lblHalfNote.setIcon(new ImageIcon(Proj2Panel.class.getResource("/project2/dottedQuarter3975.png")));
    lblHalfNote.setBounds(322, 399, 39, 75);
    add(lblHalfNote);

    JLabel lblEightNote = new JLabel("");
    lblEightNote.setIcon(new ImageIcon(Proj2Panel.class.getResource("/project2/eight7375.png")));
    lblEightNote.setBounds(408, 375, 73, 75);
    add(lblEightNote);
  }
}
