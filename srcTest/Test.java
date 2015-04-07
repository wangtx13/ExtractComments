package nanoxml;
/**
*
 */
public class XMLElement
{

    /**
     * Serialization serial version ID.
     */
    static final long serialVersionUID = 6685035139346394777L;


    


    /**
     * Returns the PCDATA content of the object. If there is no such content,
     * <CODE>null</CODE> is returned.
     *
     * @see #setContent(java.lang.String)
     *         setContent(String)
     */
    public String getContent()
    {
        return this.contents;
    }


    /**
     * Reads one XML element from a java.io.Reader and parses it.
     *
     * @param reader
     *     The reader from which to retrieve the XML data.
     *
     * </dl><dl><dt><b>Preconditions:</b></dt><dd>
     * <ul><li><code>reader != null</code>
     *     <li><code>reader</code> is not closed
     * </ul></dd></dl>
     *
     * <dl><dt><b>Postconditions:</b></dt><dd>
     * <ul><li>the state of the receiver is updated to reflect the XML element
     *         parsed from the reader
     *     <li>the reader points to the first character following the last
     *         '&gt;' character of the XML element
     * </ul></dd></dl><dl>
     *
     * @throws java.io.IOException
     *     If an error occured while reading the input.
     * @throws nanoxml.XMLParseException
     *     If an error occured while parsing the read data.
     */
    public void parseFromReader(Reader reader)
    throws IOException, XMLParseException
    {
        this.parseFromReader(reader, /*startingLineNr*/ 1);
    }


    /**
     * Reads one XML element from a java.io.Reader and parses it.
     *
     * @param reader
     *     The reader from which to retrieve the XML data.
     * @param startingLineNr
     *     The line number of the first line in the data.
     *
     * </dl><dl><dt><b>Preconditions:</b></dt><dd>
     * <ul><li><code>reader != null</code>
     *     <li><code>reader</code> is not closed
     * </ul></dd></dl>
     *
     * <dl><dt><b>Postconditions:</b></dt><dd>
     * <ul><li>the state of the receiver is updated to reflect the XML element
     *         parsed from the reader
     *     <li>the reader points to the first character following the last
     *         '&gt;' character of the XML element
     * </ul></dd></dl><dl>
     *
     * @throws java.io.IOException
     *     If an error occured while reading the input.
     * @throws nanoxml.XMLParseException
     *     If an error occured while parsing the read data.
     */
    public void parseFromReader(Reader reader,
                                int    startingLineNr)
        throws IOException, XMLParseException
    {
        this.name = null;
        this.contents = "";
        this.attributes = new HashMap();
        this.children = new ArrayList();
        this.charReadTooMuch = '\0';
        this.reader = reader;
        this.parserLineNr = startingLineNr;

        for (;;) {
            char ch = this.scanWhitespace();

            if (ch != '<') {
                throw this.expectedInput("<");
            }

            ch = this.readChar();

            if ((ch == '!') || (ch == '?')) {
                this.skipSpecialTag(0);
            } else {
                this.unreadChar(ch);
                this.scanElement(this);
                return;
            }
        }
    }

    /*
    *test
    */
    public void parseString(String string)
        throws XMLParseException
    {
        try {
            this.parseFromReader(new StringReader(string),
                                 /*startingLineNr*/ 1);
        } catch (IOException e) {
            // Java exception handling suxx
        }
    }

    /**
     * Reads one XML element from a String and parses it.
     *
     * @param string
     *     The reader from which to retrieve the XML data.
     * @param offset
     *     The first character in <code>string</code> to scan.
     * @param end
     *     The character where to stop scanning.
     *     This character is not scanned.
     * @param startingLineNr
     *     The line number of the first line in the data.
     *
     * </dl><dl><dt><b>Preconditions:</b></dt><dd>
     * <ul><li><code>string != null</code>
     *     <li><code>end &lt;= string.length()</code>
     *     <li><code>offset &lt; end</code>
     *     <li><code>offset &gt;= 0</code>
     * </ul></dd></dl>
     *
     * <dl><dt><b>Postconditions:</b></dt><dd>
     * <ul><li>the state of the receiver is updated to reflect the XML element
     *         parsed from the reader
     * </ul></dd></dl><dl>
     *
     * @throws nanoxml.XMLParseException
     *     If an error occured while parsing the string.
     */
    public void parseString(String string,
                            int    offset,
                            int    end,
                            int    startingLineNr)
        throws XMLParseException
    {
        string = string.substring(offset, end);
        try {
            this.parseFromReader(new StringReader(string), startingLineNr);
        } catch (IOException e) {
            // Java exception handling suxx
        }
    }

//test

    //test
    public void parseCharArray(char[] input,
                               int    offset,
                               int    end)
        throws XMLParseException
    {
        this.parseCharArray(input, offset, end, /*startingLineNr*/ 1);
    }