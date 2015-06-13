﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using System.Xml.Serialization;
using PSD.Repositories.Objects;

namespace PSD.Repositories
{
    public class XMLWorker
    {
        public static string Serialize(Base passwordsList)
        {
            using (StringWriter textWriter = new StringWriter())
            {
                XmlSerializerNamespaces ns = new XmlSerializerNamespaces();
                ns.Add("", "");

                XmlSerializer serializer = new XmlSerializer(typeof(Base));
                serializer.Serialize(textWriter, passwordsList, ns);
                return textWriter.ToString();
            }
        }

        public static Base Deserialize(string xml)
        {
            XmlSerializer deserializer = new XmlSerializer(typeof(Base));
            using (TextReader reader = new StringReader(xml))
            {
                Base passes = (Base)deserializer.Deserialize(reader);
                return passes;
            }
        }
    }
}