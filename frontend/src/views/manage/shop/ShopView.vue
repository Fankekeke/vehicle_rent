<template>
  <a-modal v-model="show" title="车店详情" @cancel="onClose" :width="900">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="shopData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
        <a-col :span="8"><b>车店编号：</b>
          {{ shopData.code }}
        </a-col>
        <a-col :span="8"><b>车店名称：</b>
          {{ shopData.name ? shopData.name : '- -' }}
        </a-col>
        <a-col :span="8"><b>车店地址：</b>
          {{ shopData.shopAddress ? shopData.shopAddress : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>负责人：</b>
          {{ shopData.principal }} 元
        </a-col>
        <a-col :span="8"><b>联系电话：</b>
          {{ shopData.phone }}
        </a-col>
        <a-col :span="8"><b>营业状态：</b>
          <span v-if="shopData.delFlag == 0" style="color: red">休息</span>
          <span v-if="shopData.delFlag == 1" style="color: green">营业</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">图册</span></a-col>
        <a-col :span="24">
          <a-upload
            name="avatar"
            action="http://127.0.0.1:9527/file/fileUpload/"
            list-type="picture-card"
            :file-list="fileList"
            @preview="handlePreview"
            @change="picHandleChange"
          >
          </a-upload>
          <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
            <img alt="example" style="width: 100%" :src="previewImage" />
          </a-modal>
        </a-col>
      </a-row>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'ShopView',
  props: {
    shopShow: {
      type: Boolean,
      default: false
    },
    shopData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.shopShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      repairInfo: null,
      reserveInfo: null,
      durgList: [],
      logisticsList: [],
      current: 0,
      userInfo: null
    }
  },
  watch: {
    shopShow: function (value) {
      if (value) {
        this.dataInit(this.shopData.code)
        this.imagesInit(this.shopData.images)
        this.current = this.shopData.status
      }
    }
  },
  methods: {
    dataInit (shopCode) {
      this.$get(`/cos/shop-info/detail/${shopCode}`).then((r) => {
        this.userInfo = r.data.user
      })
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
