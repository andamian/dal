/*
 ************************************************************************
 *******************  CANADIAN ASTRONOMY DATA CENTRE  *******************
 **************  CENTRE CANADIEN DE DONNÉES ASTRONOMIQUES  **************
 *
 *  (c) 2009.                            (c) 2009.
 *  Government of Canada                 Gouvernement du Canada
 *  National Research Council            Conseil national de recherches
 *  Ottawa, Canada, K1A 0R6              Ottawa, Canada, K1A 0R6
 *  All rights reserved                  Tous droits réservés
 *
 *  NRC disclaims any warranties,        Le CNRC dénie toute garantie
 *  expressed, implied, or               énoncée, implicite ou légale,
 *  statutory, of any kind with          de quelque nature que ce
 *  respect to the software,             soit, concernant le logiciel,
 *  including without limitation         y compris sans restriction
 *  any warranty of merchantability      toute garantie de valeur
 *  or fitness for a particular          marchande ou de pertinence
 *  purpose. NRC shall not be            pour un usage particulier.
 *  liable in any event for any          Le CNRC ne pourra en aucun cas
 *  damages, whether direct or           être tenu responsable de tout
 *  indirect, special or general,        dommage, direct ou indirect,
 *  consequential or incidental,         particulier ou général,
 *  arising from the use of the          accessoire ou fortuit, résultant
 *  software.  Neither the name          de l'utilisation du logiciel. Ni
 *  of the National Research             le nom du Conseil National de
 *  Council of Canada nor the            Recherches du Canada ni les noms
 *  names of its contributors may        de ses  participants ne peuvent
 *  be used to endorse or promote        être utilisés pour approuver ou
 *  products derived from this           promouvoir les produits dérivés
 *  software without specific prior      de ce logiciel sans autorisation
 *  written permission.                  préalable et particulière
 *                                       par écrit.
 *
 *  This file is part of the             Ce fichier fait partie du projet
 *  OpenCADC project.                    OpenCADC.
 *
 *  OpenCADC is free software:           OpenCADC est un logiciel libre ;
 *  you can redistribute it and/or       vous pouvez le redistribuer ou le
 *  modify it under the terms of         modifier suivant les termes de
 *  the GNU Affero General Public        la “GNU Affero General Public
 *  License as published by the          License” telle que publiée
 *  Free Software Foundation,            par la Free Software Foundation
 *  either version 3 of the              : soit la version 3 de cette
 *  License, or (at your option)         licence, soit (à votre gré)
 *  any later version.                   toute version ultérieure.
 *
 *  OpenCADC is distributed in the       OpenCADC est distribué
 *  hope that it will be useful,         dans l’espoir qu’il vous
 *  but WITHOUT ANY WARRANTY;            sera utile, mais SANS AUCUNE
 *  without even the implied             GARANTIE : sans même la garantie
 *  warranty of MERCHANTABILITY          implicite de COMMERCIALISABILITÉ
 *  or FITNESS FOR A PARTICULAR          ni d’ADÉQUATION À UN OBJECTIF
 *  PURPOSE.  See the GNU Affero         PARTICULIER. Consultez la Licence
 *  General Public License for           Générale Publique GNU Affero
 *  more details.                        pour plus de détails.
 *
 *  You should have received             Vous devriez avoir reçu une
 *  a copy of the GNU Affero             copie de la Licence Générale
 *  General Public License along         Publique GNU Affero avec
 *  with OpenCADC.  If not, see          OpenCADC ; si ce n’est
 *  <http://www.gnu.org/licenses/>.      pas le cas, consultez :
 *                                       <http://www.gnu.org/licenses/>.
 *
 *  $Revision: 4 $
 *
 ************************************************************************
 */

package ca.nrc.cadc.dali.tables.votable;

import java.util.List;
import org.jdom2.Element;
import org.jdom2.Namespace;

public class FieldElement extends Element
{
    /**
     * Builds a FIELD Element from a TableField.
     *
     * @param field
     * @param namespace 
     */
    public FieldElement(VOTableField field, Namespace namespace)
    {
        this("FIELD", field, namespace);
    }

    /**
     * Builds an Element with the specified element name and populates
     * the Element using the TableField.
     *
     * @param elementName
     * @param field
     * @param namespace
     */
    protected FieldElement(String elementName, VOTableField field, Namespace namespace)
    {
        super(elementName, namespace);
        init(field);
    }

    private void init(VOTableField field)
    {
        if (field != null)
        {
            setFieldAttribute("name", field.getName());
            setFieldAttribute("datatype", field.getDatatype());
            setFieldAttribute("ID", field.id);
            setFieldAttribute("ucd", field.ucd);
            setFieldAttribute("unit", field.unit);
            setFieldAttribute("utype", field.utype);
            setFieldAttribute("xtype", field.xtype);
            setFieldAttribute("ref", field.ref);
            setFieldAttribute("ID", field.id);
            setArraysize(field.getArraysize(), field.isVariableSize());
            setDescription(field.description, namespace);
            setValues(field.getValues(), namespace);
        }
    }

    /**
     * Set a String or Integer FIELD attribute.
     * @param name
     * @param value
     */
    protected void setFieldAttribute(String name, Object value)
    {
        if (value != null)
        {
            if (value instanceof String)
            {
                setAttribute(name, (String) value);
            }
            else if (value instanceof Integer)
            {
                setAttribute(name, String.valueOf((Integer) value));
            }
        }
    }

    /**
     * Add a DESCRIPTION Element to the FIELD.
     * @param description
     * @param namespace
     */
    protected void setDescription(String description, Namespace namespace)
    {
        if (description != null)
        {
            Element element = new Element("DESCRIPTION", namespace);
            element.setText(description);
            addContent(element);
        }
    }

    /**
     * Set the arraysize attribute.
     * @param arraysize
     * @param variableSize
     */
    protected void setArraysize(Integer arraysize, boolean variableSize)
    {
        if (arraysize != null)
        {
            if (variableSize)
            {
                setFieldAttribute("arraysize", arraysize.toString() + "*");
            }
            else
            {
                setFieldAttribute("arraysize", arraysize.toString());
            }
        }
        else if (variableSize)
        {
            setFieldAttribute("arraysize", "*");
        }
    }

    /**
     * Add VALUES Element with OPTION child Elements.
     * @param values
     * @param namespace
     */
    protected void setValues(List<String> values, Namespace namespace)
    {
        if (values != null && !values.isEmpty())
        {
            Element element = new Element("VALUES", namespace);
            for (String value : values)
            {
                Element option = new Element("OPTION", namespace);
                option.setAttribute("value", value);
                element.addContent(option);
            }
            addContent(element);
        }
    }
    
}
