Ext.define('SafetyCheck.controller.PersonsController', {
    extend: 'Ext.app.Controller',

    stores: ['PersonStore'],

    models: ['PersonModel'],

    views: ['PersonsView'],
    
    refs: [{
        selector: 'personsview grid',
        ref: 'personsGrid'
    }, {
        selector: 'personsview form',
        ref: 'personForm'
    }, {
        selector: 'personsview #onlineSyncMsg',
        ref: 'onlineSyncMsg'
    }],
    
    init: function() {
    	var personStore = this.getPersonStoreStore();
    	personStore.getProxy().on('exception', this.onException, this);
        this.listen({
            store: {
//                '#ContactsOffline': {
//                    refresh: this.onDataChange
//                },
                '#PersonStore': {
                    beforesync: this.showSyncingMsg
                }
            },
            component: {
                'personsview button[itemId=reset]': {
                    click: this.onReset
                },
                'personsview button[itemId=create]': {
                    click: this.addPerson
                },
                'personsview button[itemId=update]': {
                    click: this.saveContact
                },
                'personsview button[itemId=clear]': {
                    click: this.clearFilter
                },
                'personsview #search': {
                    change: this.filterPersons
                },
                'personsview grid': {
                    itemdblclick: this.onDoubleClick
                }
            }
        });
		var personsUrl = "api/persons";
		var queryParam = location.search.substr(1);
		if(queryParam != '') {
			personsUrl = personsUrl + "?" + queryParam;
		}
		personStore.load({
			url: personsUrl
		});
    },
    removePerson: function(gridView, rowIndex, colIndex, item, e) {
        var selection = gridView.getStore().getAt(rowIndex);
        if (selection) {
            gridView.getStore().remove(selection);
        }
    },
    filterPersons: function(txtfld, searchValue) {
        var personStore = this.getPersonStoreStore();
        //Ext.Msg.alert('Notice', 'You are in online mode', Ext.emptyFn);
    	var reg = new RegExp(searchValue, "i");
    	personStore.filterBy(function(record, id) {
        	return (reg.test(record.get("id")) || reg.test(record.get("name")) || reg.test(record.get("coordinates")) || reg.test(record.get("location")));
        }, this);
    
    },
    //        If app is offline a Proxy exception will be thrown. If that happens then use
    //        the fallback / local stoage store instead
    onException: function(proxy, response, operation) {
        //localStore.load(); // This causes the "loading" mask to disappear
        //this.getContactsGrid().getView().setLoading(false);
        this.getOnlineSyncMsg().setValue("Exception occured in fetching data...");
        this.getOnlineSyncMsg().setFieldStyle({"color": "red"});
        //Ext.Msg.alert('Notice', 'You are in offline mode', Ext.emptyFn); //alert the user that they are in offline mode
    },
    showSyncingMsg: function(){
    	this.getOnlineSyncMsg().setValue("Trying to connect to server...");
    	this.getOnlineSyncMsg().setFieldStyle({"color": "blue"});
    },
    clearFilter: function(btn) {
    	btn.previousSibling('#search').setValue('');
    },
    onDoubleClick: function(grid, record) {
    	var id = record.get('id');
    	var url = 'earthquakes.html?person=' + id.split('#')[1];
    	window.location = url;
    }
});
