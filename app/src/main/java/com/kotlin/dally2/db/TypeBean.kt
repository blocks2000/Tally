package com.kotlin.dally2.db

class TypeBean {
    var id = 0
    var typename //类型名称
            : String? = null
    var imageId=0//未被选中图片id = 0
    var simageId=0 //被选中图片id = 0
    var kind=0//收入=1，支出=0 = 0

    constructor(id: Int, typename: String?, imageId: Int, simageId: Int, kind: Int) {
        this.id = id
        this.typename = typename
        this.imageId = imageId
        this.simageId = simageId
        this.kind = kind
    }

    constructor() {}

}