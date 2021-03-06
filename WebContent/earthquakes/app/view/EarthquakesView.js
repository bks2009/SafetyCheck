Ext.define('SafetyCheck.view.EarthquakesView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.earthquakesview',
    requires: [
               'SafetyCheck.view.EarthquakeGrid'
    ],
    //title: 'safetycheck',
    //frame: true,
    padding: '50 10% 0 10%',
    bodyPadding: 20,
    fieldDefaults: {
        labelAlign: 'left'
    },
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    autoScroll: true,
    border: 0,
    items: [{
        xtype: 'form',
        activeRecord: null,
        hidden: true,
        frame: true,
        defaultType: 'textfield',
        bodyPadding: 5,
        jsonSubmit: true,
        fieldDefaults: {
            //anchor: '100%',
            labelAlign: 'right',
            //msgTarget: 'side',
            width: 300
        },
        items: [{
            xtype: 'numberfield',
            fieldLabel: 'Magnitude',
            name: 'magnitude',
            value: 4.3,
            allowBlank: false
        }, /* {
            //xtype: 'timefield',
            fieldLabel: 'Time',
            name: 'time',
            //value: new Date().getTime(),
            value: '2015-11-12T00:22:32.520Z',
            allowBlank: false
        },*/ {
            xtype: 'numberfield',
            fieldLabel: 'Latitude',
            name: 'latitude',
            value: 33.5,
            allowBlank: false
        }, {
            xtype: 'numberfield',
            fieldLabel: 'Longitude',
            name: 'longitude',
            value: -112,
            allowBlank: false
        }, {
            xtype: 'textarea',
            fieldLabel: 'Description',
            name: 'desc',
            value: 'An earthquake with magnitude 4.3 occurred near Tempe, AZ (Note: dummy earthquake)',
            allowBlank: false
        }],
        dockedItems: [{
            xtype: 'toolbar',
            dock: 'bottom',
            ui: 'footer',
            items: [{
                itemId: 'update',
                text: 'Save',
                disabled: true
            }, {
                text: 'Add Earthquake',
                itemId: 'create'
            }, {
                text: 'Cancel',
                itemId: 'reset'
            }]
        }],
        setActiveRecord: function(record) {
            this.activeRecord = record;
            if (record) {
                this.show();
                this.down('#update').enable();
                this.getForm().loadRecord(record);
            } else {
                this.down('#update').disable();
                this.getForm().reset();
            }
        }
    }, {
        xtype: 'splitter'
    }, {
        xtype: 'splitter'
    }, {
        xtype: 'fieldcontainer',
        layout: 'hbox',
        items: [{
            xtype: 'textfield',
            itemId: 'search',
            flex: 1
        }, {
            xtype: 'splitter'
        }, {
            xtype: 'button',
            text: 'Search'
        }, {
            xtype: 'splitter'
        }, {
            xtype: 'button',
            text: 'Clear Filters',
            itemId: 'clear'
        }]
    }, {
        xtype: 'splitter'
    }, {
        xtype: 'box',
        html: 'Double click an earthquake to show impacted persons'
    }, {
        xtype: 'splitter'
    }, {
        xtype: 'earthquakegrid',
        flex: 1
    }],
    dockedItems: [{
        xtype: 'toolbar',
        dock: 'top',
        items: [{
            xtype: 'displayfield',
            itemId: 'onlineSyncMsg'
            },
            '->', {
            xtype: 'button',
            text: '+ Add',
            enableToggle: true,
            toggleHandler: function(btn, state) {
                if (state) {
                    btn.up('earthquakesview').down('form').show();
                } else {
                    btn.up('earthquakesview').down('form').hide();
                }
            }
        }]
    }]
});