<template>
  <a-modal v-model="show" title="维修详情" @cancel="onClose" :width="1200">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="repairInfo !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">维修信息</span></a-col>
        <a-col :span="6"><b>维修单号：</b>
          {{ repairInfo.code }}
        </a-col>
        <a-col :span="6"><b>维修原因：</b>
          {{ repairInfo.reason ? repairInfo.reason : '- -' }}
        </a-col>
        <a-col :span="6"><b>维修金额：</b>
          {{ repairInfo.price ? repairInfo.price : '- -' }} 元
        </a-col>
        <a-col :span="6"><b>办理人：</b>
          {{ repairInfo.chargePerson }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="6"><b>维修开始时间：</b>
          {{ repairInfo.repairStart }}
        </a-col>
        <a-col :span="6"><b>维修结束时间：</b>
          {{ repairInfo.repairEnd }}
        </a-col>
        <a-col :span="6"><b>车辆状态：</b>
          <span v-if="repairInfo.repairStatus == 0" style="color: red">未完成</span>
          <span v-if="repairInfo.repairStatus == 1" style="color: green">已完成</span>
        </a-col>
        <a-col :span="6"><b>创建时间：</b>
          {{ repairInfo.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="6"><b>维修店地址：</b>
          {{ repairInfo.shopAddress }}
        </a-col>
        <a-col :span="6"><b>维修店名称：</b>
          {{ repairInfo.repairShop }}
        </a-col>
        <a-col :span="12"><b>备 注：</b>
          {{ repairInfo.content }}
        </a-col>
      </a-row>
    </div>
    <div style="font-size: 13px;font-family: SimHei" v-if="vehicleInfo !== null">
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
        <a-col :span="24"><b>备注：</b>
          {{ vehicleInfo.content }}
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
  </a-modal>
</template>

<script>
import baiduMap from '@/utils/map/baiduMap'
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
  name: 'repairView',
  props: {
    repairShow: {
      type: Boolean,
      default: false
    },
    repairData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.repairShow
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
      repairInfo: null,
      shopInfo: null,
      vehicleInfo: null
    }
  },
  watch: {
    repairShow: function (value) {
      if (value) {
        this.dataInit(this.repairData.id)
      }
    }
  },
  methods: {
    local (repairData) {
      baiduMap.clearOverlays()
      baiduMap.rMap().enableScrollWheelZoom(true)
      // eslint-disable-next-line no-undef
      let point = new BMap.Point(repairData.longitude, repairData.latitude)
      baiduMap.pointAdd(point)
      baiduMap.findPoint(point, 16)
      // let driving = new BMap.DrivingRoute(baiduMap.rMap(), {renderOptions:{map: baiduMap.rMap(), autoViewport: true}});
      // driving.search(new BMap.Point(this.nowPoint.lng,this.nowPoint.lat), new BMap.Point(scenic.point.split(",")[0],scenic.point.split(",")[1]));
    },
    dataInit (repairId) {
      this.$get(`/cos/vehicle-info/repair/detail/${repairId}`).then((r) => {
        this.repairInfo = r.data.repair
        this.shopInfo = r.data.shop
        this.vehicleInfo = r.data.vehicle
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
