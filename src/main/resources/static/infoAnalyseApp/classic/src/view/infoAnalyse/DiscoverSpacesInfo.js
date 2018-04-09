Ext.define('Admin.view.infoAnalyse.DiscoverSpacesInfo', {
    //extend: 'Ext.Container',
    //xtype: 'discoverSpacesInfo',
    //controller: 'buttons',
    extend: 'Ext.panel.Panel',
    xtype: 'discoverSpacesInfo',
    controller: 'advanced-tabs',

    width: 700,
    height: 400,
    layout: 'fit',
    viewModel: true,

    tbar: [{
        xtype: 'label',
        text: 'Position:',
        padding: '0 0 0 5'
    }, {
        xtype: 'segmentedbutton',
        reference: 'positionBtn',
        value: 'top',
        defaultUI: "default-toolbar",
        items: [{
            text: 'Top',
            value: 'top'
        }, {
            text: 'Right',
            value: 'right'
        }, {
            text: 'Bottom',
            value: 'bottom'
        }, {
            text: 'Left',
            value: 'left'
        }]
    }, {
        xtype: 'label',
        text: 'Rotation:',
        padding: '0 0 0 5'
    }, {
        xtype: 'segmentedbutton',
        reference: 'rotationBtn',
        value: 'default',
        defaultUI: "default-toolbar",
        items: [{
            text: 'Default',
            value: 'default'
        }, {
            text: 'None',
            value: 0
        }, {
            text: '90deg',
            value: 1
        }, {
            text: '270deg',
            value: 2
        }]
    }, {
        xtype: 'button',
        icon: null,
        glyph: 43,
        tooltip: 'Add Tab',
        listeners: {
            click: 'onAddTabClick'
        }
    }, {
        xtype: 'button',
        enableToggle: true,
        tooltip: 'Auto Cycle!',
        listeners: {
            toggle: 'onAutoCycleToggle'
        },
        glyph: 109
    }],

    items: [{
        xtype: 'tabpanel',
        reference: 'tabpanel',
        border: false,
        defaults: {
            bodyPadding: 10,
            scrollable: true,
            closable: true,
            border: false
        },
        bind: {
            tabPosition: '{positionBtn.value}',
            tabRotation: '{rotationBtn.value}'
        },
        items: [{
            title: 'Tab 1',
            icon: null,
            glyph: 42,
            html: '<div class=\'fa-outer-class\'><span class=\'x-fa fa-clock-o\'></span></div><h1>Coming Soon!</h1><span class=\'blank-page-text\'>Stay tuned for updates</span>'
        }, {
            title: 'Tab 2',
            icon: null,
            glyph: 70,
            html: '<div class=\'fa-outer-class\'><span class=\'x-fa fa-clock-o\'></span></div><h1>Coming Soon!</h1><span class=\'blank-page-text\'>Stay tuned for updates</span>'
        }, {
            title: 'Tab 3',
            icon: null,
            glyph: 86,
            html: '<div class=\'fa-outer-class\'><span class=\'x-fa fa-clock-o\'></span></div><h1>Coming Soon!</h1><span class=\'blank-page-text\'>Stay tuned for updates</span>'
        }]
    }]

});
