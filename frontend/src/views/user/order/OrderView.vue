<template>
  <a-modal v-model="show" title="订单详情" @cancel="onClose" :width="900">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="orderData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
        <a-col :span="8"><b>订单编号：</b>
          {{ orderData.code }}
        </a-col>
        <a-col :span="8"><b>订单名称：</b>
          {{ orderData.orderName ? orderData.orderName : '- -' }}
        </a-col>
        <a-col :span="8"><b>价格/日：</b>
          {{ orderData.dayPrice ? orderData.dayPrice + '元' : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>开始租车时间：</b>
          {{ orderData.startDate }}
        </a-col>
        <a-col :span="8"><b>归还车辆时间：</b>
          {{ orderData.endDate ? orderData.endDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>总价格：</b>
          {{ orderData.total ? orderData.total + '元' : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>订单状态：</b>
          <span v-if="orderData.status == -1">未支付</span>
          <span v-if="orderData.status == 0">未完成</span>
          <span v-if="orderData.status == 1">已完成</span>
        </a-col>
        <a-col :span="8"><b>取车店铺：</b>
          {{ orderData.takeShop ? orderData.takeShop : '- -' }}
        </a-col>
        <a-col :span="8"><b>归还店铺：</b>
          {{ orderData.returnShop ? orderData.returnShop : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>下单时间：</b>
          {{ orderData.createDate ? orderData.createDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>备注：</b>
          {{ orderData.remark ? orderData.remark : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="vehicleInfo != null">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">车辆信息</span></a-col>
        <a-col :span="8"><b>车辆编号：</b>
          {{ vehicleInfo.vehicleNo }}
        </a-col>
        <a-col :span="8"><b>车牌号：</b>
          {{ vehicleInfo.vehicleNumber }}
        </a-col>
        <a-col :span="8"><b>车辆颜色：</b>
          {{ vehicleInfo.vehicleColor }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="vehicleInfo != null">
        <a-col :span="8"><b>车辆名称：</b>
          {{ vehicleInfo.name }}
        </a-col>
        <a-col :span="8"><b>发动机号码：</b>
          {{ vehicleInfo.engineNo }}
        </a-col>
        <a-col :span="8"><b>载客数量：</b>
          {{ vehicleInfo.carryPassengers }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="vehicleInfo != null">
        <a-col :span="8"><b>负责人：</b>
          {{ vehicleInfo.principal }}
        </a-col>
        <a-col :span="8"><b>联系电话：</b>
          {{ vehicleInfo.phone }}
        </a-col>
        <a-col :span="8"><b>出场日期：</b>
          {{ vehicleInfo.factoryDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="vehicleInfo != null">
        <a-col :span="8"><b>排放标准：</b>
          {{ vehicleInfo.emissionStandard }}
        </a-col>
        <a-col :span="8"><b>燃料类型：</b>
          <span v-if="vehicleInfo.fuelType == 1">燃油</span>
          <span v-if="vehicleInfo.fuelType == 2">柴油</span>
          <span v-if="vehicleInfo.fuelType == 3">油电混动</span>
          <span v-if="vehicleInfo.fuelType == 4">电能</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="vehicleInfo != null">
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
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="userInfo !== null">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">用户信息</span></a-col>
        <a-col :span="8"><b>客户编号：</b>
          {{ userInfo.code }}
        </a-col>
        <a-col :span="8"><b>客户名称：</b>
          {{ userInfo.name }}
        </a-col>
        <a-col :span="8"><b>联系方式：</b>
          {{ userInfo.phone }}
        </a-col>
      </a-row>
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
  name: 'orderView',
  props: {
    orderShow: {
      type: Boolean,
      default: false
    },
    orderData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.orderShow
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
      userInfo: null,
      orderInfo: null,
      vehicleInfo: null
    }
  },
  watch: {
    orderShow: function (value) {
      if (value) {
        this.dataInit(this.orderData.code)
      }
    }
  },
  methods: {
    dataInit (orderCode) {
      this.$get(`/cos/vehicle-info/order/detail/${orderCode}`).then((r) => {
        this.userInfo = r.data.user
        this.vehicleInfo = r.data.vehicle
        this.orderInfo = r.data.order
        this.imagesInit(this.vehicleInfo.images)
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
