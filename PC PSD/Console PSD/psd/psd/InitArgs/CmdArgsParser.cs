﻿using System;
using System.Linq;

namespace psd.InitArgs
{
    static class CmdArgsParser
    {

        public static Args Parse(Args args, string[] strArgs)
        {

            var success = ParseCmdType(strArgs, args);

            args.PcPath = GetArgVal(strArgs, "-b");
            args.PhonePath = GetArgVal(strArgs, "-p");
            args.UsePsd = strArgs.Contains("--usepsd");
            args.Help = strArgs.Contains("--help");

            if (!success || args.Help)
            {
                PrintHelp();
                return null;
            }
            return args;
        }


        private static bool ParseCmdType(string[] strArgs, Args args)
        {
            switch (strArgs[0])
            {
                case "list":
                    args.CmdType = CommandType.ListPasses;
                    break;
                case "add":
                    args.CmdType = CommandType.AddPass;
                    break;
                case "rem":
                    args.CmdType = CommandType.RemovePass;
                    break;
                case "edit":
                    args.CmdType = CommandType.EditPass;
                    break;
                case "info":
                    args.CmdType = CommandType.ShowPassInfo;
                    break;
                default:
                    return false;
            }
            return true;
        }

        private static String GetArgVal(string[] strArgs, String argKey)
        {
            int indexOfArgValue = Array.IndexOf(strArgs, argKey) + 1;
            if (indexOfArgValue == 0)
                return null;
            return strArgs.ElementAtOrDefault(indexOfArgValue);
        }

        private static String GetArgVal(string[] strArgs, String argKey, String longKey)
        {
            String result;
            result = GetArgVal(strArgs, argKey);
            if (result == null)
                result = GetArgVal(strArgs, longKey);
            return result;
        }

        public static void PrintHelp()
        {
            Console.WriteLine("Help: ");
            Console.WriteLine("Commands: ");
            Console.WriteLine("list");
            Console.WriteLine("add");
            Console.WriteLine("rem");
            Console.WriteLine("edit");
            Console.WriteLine("info");
            Console.WriteLine("Args: ");
            Console.WriteLine("-b - PC base path");
            Console.WriteLine("-p - Phone base path");
            Console.WriteLine("--usepsd - Find and conect PSD base");
        }
    }
}
