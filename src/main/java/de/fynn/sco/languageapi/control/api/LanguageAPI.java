package de.fynn.sco.languageapi.control.api;

import de.fynn.sco.languageapi.control.language.LanguageManager;
import de.fynn.sco.languageapi.model.exception.InvalidLanguageFileException;
import de.fynn.sco.languageapi.model.file.LanguageFile;
import de.fynn.sco.languageapi.utils.FileUtils;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author Freddyblitz
 * @version 1.0
 */
public class LanguageAPI {

    /*----------------------------------------------ATTRIBUTE---------------------------------------------------------*/

    private final Plugin parent;

    private final static LanguageManager languageManager = LanguageManager.getInstance();

    /*--------------------------------------------KONSTRUKTOREN-------------------------------------------------------*/

    /**
     * Der Konstruktor benoetigt das Plugin, fuer welches die Instanz der LanguageAPI erstellt wird,
     * um Anfragen fuer uebersetzungen den richtigen LanguageFiles zuordnen zu koennen. Ausserdem wird
     * zur instanzierung mindestens ein LanguageFile benoetigt.
     * Dies ist unter anderem die standart Sprache des Plugins und wird verwendet, wenn ein Spieler eine Sprache eingestellt
     * hat, die von dem regestrierten Plugin nicht bereitgestellt wird.
     * @param parent das Plugin, welches die API benutzen moechte
     * @param defaultLanguageFile eine Datei, die eine Sprache beinhaltet
     * @throws InvalidLanguageFileException
     */
    public LanguageAPI(Plugin parent, File defaultLanguageFile) throws InvalidLanguageFileException {
        if (!FileUtils.validateLanguageFile(defaultLanguageFile)) throw new InvalidLanguageFileException();
        this.parent = parent;
        languageManager.registerPlugin(parent);
    }

    /**
     * Der Konstruktor benoetigt das Plugin, fuer welches die Instanz der LanguageAPI erstellt wird,
     * um Anfragen fuer uebersetzungen den richtigen LanguageFiles zuordnen zu koennen. Ausserdem wird
     * zur instanzierung mindestens ein InputStream benoetigt, der eine Language-Datei beinhaltet.
     * Dies ist unter anderem die standart Sprache des Plugins und wird verwendet, wenn ein Spieler eine Sprache eingestellt
     * hat, die von dem regestrierten Plugin nicht bereitgestellt wird.
     * @param parent das Plugin, welches die API benutzen moechte
     * @param defaultLanguageFileInputStream ein Stream, der die Language-Datei beinhaltet
     * @throws InvalidLanguageFileException
     */
    public LanguageAPI(Plugin parent, InputStream defaultLanguageFileInputStream) throws InvalidLanguageFileException {
        if (!FileUtils.validateLanguageFile(defaultLanguageFileInputStream)) throw new InvalidLanguageFileException();
        this.parent = parent;
        languageManager.registerPlugin(parent);
    }

    /*----------------------------------------------METHODEN----------------------------------------------------------*/

    /**
     * Wird verwendet, um weitere unterstuetzte Sprachen hinzuzufuegen. Das File Objekt muss eine .language Datei sein.
     * @param languageFile die Language-Datei, in der die Sprache definiert ist
     * @throws InvalidLanguageFileException
     */
    public void registerLanguage(File languageFile) throws InvalidLanguageFileException {
        if (FileUtils.validateLanguageFile(languageFile)){
            languageManager.registerLanguage(parent, new LanguageFile(languageFile));
        } else {
            throw new InvalidLanguageFileException();
        }
    }

    /**
     * Wird verwendet, um weitere unterstuetzte Sprachen hinzuzufuegen. Der InputStream muss eine .language Datei sein.
     * @param languageFileInputStream ein InputStream, der die Language-Datei beinhaltet
     * @throws InvalidLanguageFileException
     */
    public void registerLanguage(InputStream languageFileInputStream) throws InvalidLanguageFileException{
        if (FileUtils.validateLanguageFile(languageFileInputStream)){
            languageManager.registerLanguage(parent, new LanguageFile(languageFileInputStream));
        } else {
            throw new InvalidLanguageFileException();
        }
    }

    /**
     * Gibt die ubersetzung fuer einen bestimmten String in der Sprache, die der betroffene Spieler eingestellt hat zurueck.
     * @param playerUUID Die UUID des Spielers, fuer den die Ubersetzung benoetigt wird.
     * @param messageKey Ein String, der einen Key in der Language-Datei repraesentiert
     * @return Die geforderte uberstzung fuer den Spieler wenn der Key exestiert, ansonsten null
     */
    public String getTranslation(UUID playerUUID, String messageKey){
        return languageManager.getTranslation(parent, playerUUID, messageKey);
    }

}
