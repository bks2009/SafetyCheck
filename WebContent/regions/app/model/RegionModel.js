Ext.define('SafetyCheck.model.RegionModel', {
    extend: 'Ext.data.Model',
    idProperty: "uri", //erm, primary key
    fields: [{
        name: 'id',
        type: 'string'
    }, {
        name: 'name',
        type: 'string'
    }, {
		name : 'latitude',
		type : 'number'
	}, {
		name : 'longitude',
		type : 'number'
	}, {
		name : 'population',
		type : 'number'
	}, {
		name : 'coordinates',
		type : 'string',
		convert : function(v, rec) {
			return rec.get('latitude') + " " + rec.get('longitude');
		}
	}]//,
//    identifier: 'uuid' // IMPORTANT, needed to avoid console warnings!
});