(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0da705"],{"6c35":function(t,e,s){"use strict";s.r(e);var i=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",[s("el-dialog",{attrs:{title:"add"===t.addEditType?"添加角色":"编辑角色",visible:t.addDialogVisible,fullscreen:"",center:""},on:{"update:visible":function(e){t.addDialogVisible=e}}},[s("el-form",{ref:"addForm",staticStyle:{width:"40%",margin:"auto"},attrs:{model:t.addForm,"label-width":"120px",rules:t.addFormRules,"status-icon":""}},[s("el-form-item",{attrs:{label:"所属系统",prop:"systemId"}},[s("el-select",{attrs:{clearable:""},on:{change:t.getPermissionAttrList},model:{value:t.addForm.systemId,callback:function(e){t.$set(t.addForm,"systemId",e)},expression:"addForm.systemId"}},t._l(t.availSystemAttrList,function(t){return s("el-option",{key:t.id,attrs:{label:t.name,value:t.id}})}),1)],1),s("el-form-item",{attrs:{label:"名称",prop:"name"}},[s("el-input",{model:{value:t.addForm.name,callback:function(e){t.$set(t.addForm,"name",e)},expression:"addForm.name"}})],1),s("el-form-item",{attrs:{label:"描述",prop:"description"}},[s("el-input",{attrs:{type:"textarea",rows:"5",resize:"vertical"},model:{value:t.addForm.description,callback:function(e){t.$set(t.addForm,"description",e)},expression:"addForm.description"}})],1),s("el-form-item",{attrs:{label:"权限配置"}},[s("el-tree",{ref:"treeRef",attrs:{data:t.permissionAttrList,props:t.defaultProps,"show-checkbox":"","node-key":"id","highlight-current":""}})],1)],1),s("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[s("el-button",{attrs:{type:"default"},on:{click:t.addEditRoleCancel}},[t._v("取 消")]),s("el-button",{attrs:{type:"primary"},on:{click:t.addEditRoleConfirm}},[t._v("确 定")])],1)],1),s("el-form",{ref:"searchForm",attrs:{inline:!0,model:t.searchForm}},[s("el-form-item",{attrs:{label:"名称",prop:"name"}},[s("el-input",{attrs:{placeholder:"名称"},on:{input:t.searchRoleList},model:{value:t.searchForm.name,callback:function(e){t.$set(t.searchForm,"name",e)},expression:"searchForm.name"}})],1),s("el-form-item",{attrs:{label:"所属系统",prop:"systemId"}},[s("el-select",{on:{change:t.searchRoleList},model:{value:t.searchForm.systemId,callback:function(e){t.$set(t.searchForm,"systemId",e)},expression:"searchForm.systemId"}},[s("el-option",{attrs:{label:"请选择",value:""}}),t._l(t.systemAttrList,function(t){return s("el-option",{key:t.id,attrs:{label:t.name,value:t.id}})})],2)],1),s("el-form-item",[s("el-button",{attrs:{type:"primary"},on:{click:t.searchRoleList}},[t._v("查询")]),s("el-button",{attrs:{type:"default"},on:{click:t.searchFormReset}},[t._v("重置")]),s("el-button",{attrs:{type:"success"},on:{click:function(e){return t.openRoleDialog("add")}}},[t._v("添加")])],1)],1),s("el-table",{staticStyle:{width:"100%"},attrs:{border:"",data:t.list}},[s("el-table-column",{attrs:{label:"ID",width:"40"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\n        "+t._s(e.row.id)+"\n      ")]}}])}),s("el-table-column",{attrs:{label:"名称",width:"180"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\n        "+t._s(e.row.name)+"\n      ")]}}])}),s("el-table-column",{attrs:{label:"描述",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\n        "+t._s(e.row.description)+"\n      ")]}}])}),s("el-table-column",{attrs:{label:"所属系统",width:"180"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\n        "+t._s(e.row.systemName)+"\n      ")]}}])}),s("el-table-column",{attrs:{label:"权限"},scopedSlots:t._u([{key:"default",fn:function(e){return t._l(e.row.permissions,function(e){return s("el-tag",{key:e.id,attrs:{size:"mini",type:"info"}},[t._v(t._s(e.name)+"\n        ")])})}}])}),s("el-table-column",{attrs:{label:"操作时间",width:"180"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\n        "+t._s(e.row.operateTime)+"\n      ")]}}])}),s("el-table-column",{attrs:{label:"操作人",width:"100"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\n        "+t._s(e.row.operator)+"\n      ")]}}])}),s("el-table-column",{attrs:{label:"操作"},scopedSlots:t._u([{key:"default",fn:function(e){return[s("el-button",{attrs:{plain:"",type:"primary",size:"mini"},on:{click:function(s){return t.openRoleDialog("edit",e.row)}}},[t._v("编辑")]),s("el-button",{attrs:{plain:"",type:"danger",size:"mini"},on:{click:function(s){return t.deleteRole(e.row.id)}}},[t._v("删除")])]}}])})],1)],1)},a=[],r=(s("7f7f"),s("cebc")),n=s("700d"),o={name:"Role",data:function(){return{systemAttrList:null,availSystemAttrList:null,permissionAttrList:null,defaultProps:{children:"children",label:"name"},addEditType:"add",addDialogVisible:!1,addForm:{systemId:"",name:"",description:""},addFormRules:{systemId:[{required:!0,message:"请选择所属系统",trigger:"change"}],name:[{required:!0,message:"请输入角色名称",trigger:"blur"},{min:2,max:20,message:"长度在 2 到 20 个字符",trigger:"blur"}],description:[{min:1,max:50,message:"长度在 1 到 50 个字符",trigger:"blur"}]},searchForm:{name:"",systemId:""},list:null,currentPage:1,pageSize:100,total:1}},mounted:function(){this.getSystemAttrList(),this.getAvailSystemAttrList(),this.searchRoleList()},methods:{getSystemAttrList:function(){var t=this;this.$get(n["a"].getSystemAttrList,null).then(function(e){var s=e.data;t.systemAttrList=s})},getAvailSystemAttrList:function(){var t=this;this.$get(n["a"].getSystemAttrList,{disableFlag:0}).then(function(e){var s=e.data;t.availSystemAttrList=s})},getPermissionAttrList:function(){var t=this;this.permissionAttrList=null;var e=this.addForm.systemId;if(e)return this.$get(n["a"].getPermissionAttrList,{systemId:e}).then(function(e){var s=e.data;t.permissionAttrList=s})},searchRoleList:function(){var t=this;this.$getLoading(n["a"].searchRoleList,Object(r["a"])({currentPage:this.currentPage,pageSize:this.pageSize},this.searchForm)).then(function(e){var s=e.data;t.list=s.list,t.total=s.total})},searchFormReset:function(){this.$refs.searchForm.resetFields(),this.searchRoleList()},openRoleDialog:function(t,e){this.addDialogVisible=!0,this.addEditType=t,e&&(this.addForm={id:e.id,systemId:e.systemId,name:e.name,description:e.description},this.getPermissionAttrList().then(function(){this.$refs.treeRef.setCheckedKeys(e.permissions.map(function(t){return t.id}))}.bind(this)))},addEditRoleCancel:function(){this.addDialogVisible=!1,this.$refs.addForm.resetFields()},addEditRoleConfirm:function(){var t=this;this.$refs.addForm.validate(function(e){if(!e)return!1;var s=t.$refs.treeRef.getCheckedKeys().concat(t.$refs.treeRef.getHalfCheckedKeys()),i={id:t.addForm.id,systemId:t.addForm.systemId,name:t.addForm.name,description:t.addForm.description,permissionIds:s||[]};t.$postJsonLoading("add"===t.addEditType?n["a"].addRole:n["a"].editRole,i).then(function(e){t.addEditRoleCancel(),t.searchRoleList()})})},deleteRole:function(t){var e=this;this.$confirm("确定删除该角色？","警告",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){e.$post(n["a"].deleteRole,{roleId:t}).then(function(t){e.searchRoleList()})})}}},l=o,d=s("2877"),c=Object(d["a"])(l,i,a,!1,null,"574eef3a",null);e["default"]=c.exports}}]);
//# sourceMappingURL=chunk-2d0da705.e7ce281a.js.map