﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using PSD.Config;
using PSD.Device.Hid;
using PSD.Locales;
using PSD.Properties;
using PSD.Repositories;

namespace PSD
{
    public partial class PrepareForm : Form
    {
        public DataConnections DataConnections { get; set; } = new DataConnections();
        public bool StartApp { get; private set; } = false;


        public PrepareForm()
        {
            InitializeComponent();
        }

        private void PrepareForm_Load(object sender, EventArgs e)
        {
        }

        private void txtPassword_TextChanged(object sender, EventArgs e)
        {

        }

        private void btnConnectPsd_Click(object sender, EventArgs e)
        {
            TryConnectPSDBase();
            RebindAll();
        }


        private void btnSet_Click(object sender, EventArgs e)
        {
            if (txtPassword.Enabled)
            {
                btnSet.Text = Localization.btnUnsetText;

                DataConnections.UserPasses = new BasePasswords(txtPassword.Text);
                ReinitPsds();
                RebindAll();
            }
            else
            {
                btnSet.Text = Localization.btnSetText;
            }
            SwitchEnabled();
        }


        private void RebindAll()
        {
            lblBasePath.DataBindings.Clear();
            if (DataConnections.PcBase != null)
            {
                lblBasePath.DataBindings.Add(new Binding("Text", DataConnections.PcBase, "Path"));
            }

            lblAndroidPath.DataBindings.Clear();
            if (DataConnections.PhoneBase != null)
            {
                lblAndroidPath.DataBindings.Add(new Binding("Text", DataConnections.PhoneBase, "Path"));
            }

            lblConnectedPsd.DataBindings.Clear();
            lblConnectedDesc.DataBindings.Clear();
            if (DataConnections.PsdBase != null)
            {
                lblConnectedPsd.DataBindings.Add(new Binding("Text", DataConnections.PsdBase, "Name"));
                lblConnectedPsd.DataBindings.Add(new Binding("Visible", DataConnections.PsdBase, "Connected"));
                lblConnectedDesc.DataBindings.Add(new Binding("Visible", DataConnections.PsdBase, "Connected"));
            }
        }


        private bool ReinitPsds()
        {
            var finder = new PSDFinder();
            var psds = finder.FindConnectedPsds();
            cmbPsds.Items.Clear();
            cmbPsds.Items.AddRange(psds);
            if (psds.Any())
            {
                cmbPsds.SelectedIndex = 0;
                return true;
            }
            else
            {
                return false;
            }

        }


        private void btnSelectStorageFile_Click(object sender, EventArgs e)
        {
            OpenFileDialog fileDialog = new OpenFileDialog();
            fileDialog.ShowDialog();
            TryConnectPcBase(fileDialog.FileName);
            RebindAll();
        }


        private void btnSelectPhoneFile_Click(object sender, EventArgs e)
        {
            OpenFileDialog fileDialog = new OpenFileDialog();
            fileDialog.ShowDialog();
            TryConnectAndroidBase(fileDialog.FileName);
            RebindAll();
        }

        private void TryConnectPcBase(string path)
        {
            if (!DataConnections.SetPCBase(path))
                MessageBox.Show(Localization.CantLoadFileString);
        }

        private void TryConnectAndroidBase(string path)
        {
            if (!DataConnections.SetPhoneBase(path))
                MessageBox.Show(Localization.CantLoadFileString);

        }

        private void TryConnectPSDBase()
        {
            if (!DataConnections.SetPsdDevice((PSDDevice)cmbPsds.SelectedItem))
                MessageBox.Show(Localization.PsdConnectionError);
        }



        private void btnCreateStorageFile_Click(object sender, EventArgs e)
        {
            SaveFileDialog saveFileDialog = new SaveFileDialog();
            saveFileDialog.ShowDialog();
            if (!DataConnections.SetPCBase(saveFileDialog.FileName))
            {
                DataConnections.DropPcBase();
            }
            RebindAll();
        }



        private void btnCreatePhoneFile_Click(object sender, EventArgs e)
        {
            SaveFileDialog saveFileDialog = new SaveFileDialog();
            saveFileDialog.ShowDialog();
            if (!DataConnections.SetPhoneBase(saveFileDialog.FileName))
            {
                DataConnections.DropPhoneBase();
            }
            RebindAll();
        }


        private void SwitchEnabled()
        {
            txtPassword.Enabled = !txtPassword.Enabled;

            btnSelectPhoneFile.Enabled = !btnSelectPhoneFile.Enabled;
            btnCreatePhoneFile.Enabled = !btnCreatePhoneFile.Enabled;

            btnCreateStorageFile.Enabled = !btnCreateStorageFile.Enabled;
            btnSelectStorageFile.Enabled = !btnSelectStorageFile.Enabled;

            cmbPsds.Enabled = !cmbPsds.Enabled;
            btnConnectPsd.Enabled = !btnConnectPsd.Enabled;

            btnStart.Enabled = !btnStart.Enabled;
        }



        private void btnStart_Click(object sender, EventArgs e)
        {
            if (DataConnections.PcBase == null)
                MessageBox.Show(Localization.StorageFileNotSelectedError);
            else
            {
                this.Close();
                StartApp = true;
            }
        }

        private void btnRefresh_Click(object sender, EventArgs e)
        {
            if (!ReinitPsds())
                MessageBox.Show(Localization.NoPSDsError);
        }

        private void PrepareForm_FormClosing(object sender, FormClosingEventArgs e)
        {

        }
    }
}