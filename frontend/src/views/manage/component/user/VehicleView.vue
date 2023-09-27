<template>
  <a-modal v-model="show" title="车辆预订" @cancel="onClose" :width="1200">
    <template slot="footer">
      <a-button key="back1" @click="addOrder">
        预订
      </a-button>
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="vehicleInfo !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;font-size: 25px">
        <a-col :span="12" style="text-align: center">
          开始时间:<span style="font-size: 20px">{{ startDate }}</span>
        </a-col>
        <a-col :span="12" style="text-align: center">
          结束时间:<span style="font-size: 20px">{{ endDate }}</span>
        </a-col>
      </a-row>
      <br/>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">车辆信息</span></a-col>
        <a-col :span="6"><b>车辆编号：</b>
          {{ vehicleInfo.vehicleNo }}
        </a-col>
        <a-col :span="6"><b>车牌号：</b>
          {{ vehicleInfo.vehicleNumber ? vehicleInfo.vehicleNumber : '- -' }}
        </a-col>
        <a-col :span="6"><b>车辆颜色：</b>
          {{ vehicleInfo.vehicleColor ? vehicleInfo.vehicleColor : '- -' }}
        </a-col>
        <a-col :span="6"><b>车辆名称：</b>
          {{ vehicleInfo.name }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="6"><b>载客数量：</b>
          {{ vehicleInfo.carryPassengers }}
        </a-col>
        <a-col :span="6"><b>发动机号码：</b>
          {{ vehicleInfo.engineNo }}
        </a-col>
        <a-col :span="6"><b>车辆状态：</b>
          <span v-if="vehicleInfo.status == 0" style="color: red">空闲</span>
          <span v-if="vehicleInfo.status == 1" style="color: green">使用中</span>
          <span v-if="vehicleInfo.status == 2" style="color: green">维修中</span>
          <span v-if="vehicleInfo.status == 3" style="color: green">已报废</span>
        </a-col>
        <a-col :span="6"><b>负责人：</b>
          {{ vehicleInfo.principal }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="6"><b>联系电话：</b>
          {{ vehicleInfo.phone }}
        </a-col>
        <a-col :span="6"><b>出厂日期：</b>
          {{ vehicleInfo.factoryDate }}
        </a-col>
        <a-col :span="6"><b>排放标准：</b>
          {{ vehicleInfo.emissionStandard }}
        </a-col>
        <a-col :span="6"><b>燃料类型：</b>
          <span v-if="vehicleInfo.fuelType == 1" style="color: green">燃油</span>
          <span v-if="vehicleInfo.fuelType == 2" style="color: green">柴油</span>
          <span v-if="vehicleInfo.fuelType == 3" style="color: green">油电混动</span>
          <span v-if="vehicleInfo.fuelType == 4" style="color: green">电能</span>
        </a-col>
        <br/>
        <br/>
        <a-col :span="24"><b>备注：</b>
          {{ vehicleInfo.content }}
        </a-col>
      </a-row>
      <br/>
    </div>
    <div style="font-size: 13px;font-family: SimHei" v-if="shopInfo !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">车店信息</span></a-col>
        <a-col :span="6"><b>车店编号：</b>
          {{ shopInfo.code }}
        </a-col>
        <a-col :span="6"><b>车店名称：</b>
          {{ shopInfo.name ? shopInfo.name : '- -' }}
        </a-col>
        <a-col :span="6"><b>车店地址：</b>
          {{ shopInfo.shopAddress ? shopInfo.shopAddress : '- -' }}
        </a-col>
        <a-col :span="6"><b>营业状态：</b>
          <span v-if="shopInfo.delFlag == 0" style="color: red">休息</span>
          <span v-if="shopInfo.delFlag == 1" style="color: green">营业</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="6"><b>负责人：</b>
          {{ shopInfo.principal }}
        </a-col>
        <a-col :span="6"><b>联系电话：</b>
          {{ shopInfo.phone }}
        </a-col>
      </a-row>
    </div>
    <br/>
    <div style="font-size: 13px;font-family: SimHei" v-if="brandInfo !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">车辆品牌</span></a-col>
        <a-col :span="6"><b>品牌编号：</b>
          {{ brandInfo.brandCode }}
        </a-col>
        <a-col :span="6"><b>车店名称：</b>
          {{ brandInfo.name ? brandInfo.name : '- -' }}
        </a-col>
      </a-row>
    </div>
    <br/>
    <div style="font-size: 13px;font-family: SimHei" v-if="typeInfo !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">车辆类型</span></a-col>
        <a-col :span="6"><b>类型编号：</b>
          {{ typeInfo.code }}
        </a-col>
        <a-col :span="6"><b>车辆类型名称：</b>
          {{ typeInfo.name ? typeInfo.name : '- -' }}
        </a-col>
        <a-col :span="6"><b>车门数：</b>
          {{ typeInfo.doors ? typeInfo.doors : '- -' }}
        </a-col>
        <a-col :span="6"><b>车座数：</b>
          {{ typeInfo.seats ? typeInfo.seats : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="6"><b>适合行李数：</b>
          {{ typeInfo.luggage }}
        </a-col>
        <a-col :span="6"><b>出行类别：</b>
          {{ typeInfo.travelCategory }}
        </a-col>
      </a-row>
    </div>
  </a-modal>
</template>

<script>
import baiduMap from '@/utils/map/baiduMap'
import moment from 'moment'
import {mapState} from 'vuex'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
moment.locale('zh-cn')
export default {
  name: 'rentView',
  props: {
    vehicleShow: {
      type: Boolean,
      default: false
    },
    vehicleData: {
      type: Object
    },
    startDate: {
      type: String
    },
    endDate: {
      type: String
    }
  },
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    }),
    show: {
      get: function () {
        return this.vehicleShow
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
      userInfo: null,
      vehicleInfo: null,
      shopInfo: null,
      brandInfo: null,
      typeInfo: null
    }
  },
  watch: {
    vehicleShow: function (value) {
      if (value) {
        this.imagesInit(this.vehicleData.images)
        this.dataInit(this.vehicleData.vehicleNo)
      }
    }
  },
  methods: {
    addOrder () {
      let data = {
        vehicleNo: this.vehicleInfo.vehicleNo,
        startDate: this.startDate,
        endDate: this.endDate,
        userId: this.user.userId
      }
      this.$post('/cos/vehicle-info/order/add', data).then((r) => {
        this.$emit('success')
      })
    },
    selectDetail (roomId) {
      this.$get(`/cos/room-info/detail/${roomId}`).then((r) => {
        this.roomInfo = r.data.room
        this.typeInfo = r.data.type
      })
    },
    local (vehicleData) {
      baiduMap.clearOverlays()
      baiduMap.rMap().enableScrollWheelZoom(true)
      // eslint-disable-next-line no-undef
      let point = new BMap.Point(vehicleData.longitude, vehicleData.latitude)
      baiduMap.pointAdd(point)
      baiduMap.findPoint(point, 16)
      // let driving = new BMap.DrivingRoute(baiduMap.rMap(), {renderOptions:{map: baiduMap.rMap(), autoViewport: true}});
      // driving.search(new BMap.Point(this.nowPoint.lng,this.nowPoint.lat), new BMap.Point(scenic.point.split(",")[0],scenic.point.split(",")[1]));
    },
    dataInit (vehicleNo) {
      this.$get(`/cos/vehicle-info/detail/${vehicleNo}`).then((r) => {
        this.vehicleInfo = r.data.vehicle
        this.shopInfo = r.data.shop
        this.brandInfo = r.data.brand
        this.typeInfo = r.data.type
      })
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
    picHandleChange ({fileList}) {
      this.fileList = fileList
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
    getCommunity (communityCode) {
      this.$get(`/cos/community-info/detail/${communityCode}`).then((r) => {
        this.community = r.data.data
      })
    },
    getHouseInfo (houseCode) {
      this.$get(`/cos/house-info/detail/${houseCode}`).then((r) => {
        this.houseInfo = r.data.data
        this.getCommunity(this.houseInfo.communityCode)
      })
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>
/deep/ .ant-table-tbody {
  font-size: 12px;
}
/deep/ .ant-table-thead {
  font-size: 13px;
}
</style>
